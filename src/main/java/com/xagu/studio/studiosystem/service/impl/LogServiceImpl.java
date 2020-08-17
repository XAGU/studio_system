package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.bean.Log;
import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.dao.LogRepository;
import com.xagu.studio.studiosystem.dao.WxAccountRepository;
import com.xagu.studio.studiosystem.service.ILogService;
import com.xagu.studio.studiosystem.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/8/15
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service("logService")
@Transactional(rollbackFor = RuntimeException.class)
public class LogServiceImpl extends BaseService implements ILogService {
    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    WxAccountRepository wxAccountRepository;

    @Autowired
    LogRepository logRepository;


    @Override
    public boolean addLog(Log log) {
        if (StringUtils.isEmpty(log.getMsg())) {
            return false;
        }
        String wxId = log.getWxId();
        if (StringUtils.isEmpty(wxId)) {
            return false;
        }
        WxAccount wxAccount = wxAccountRepository.findOneById(wxId);
        if (wxAccount == null) {
            return false;
        }
        log.setId(snowFlake.nextId() + "");
        log.setUpdateTime(new Date());
        logRepository.save(log);
        return true;
    }


    @Override
    public Page<Log> listLog(Integer page, Integer size, String wxId) {
        page = this.checkPage(page);
        size = this.checkSize(size);
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        //开始查询
        Page<Log> logs = logRepository.findAll(new Specification<Log>() {
            @Override
            public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(wxId)) {
                    predicates.add(criteriaBuilder.equal(root.get("wxId"), wxId));
                }
                Predicate[] predicate = new Predicate[predicates.size()];
                predicates.toArray(predicate);
                return criteriaBuilder.and(predicate);
            }
        }, pageable);
        return logs;
    }

    @Override
    public boolean clearLog(String wxId) {
        return logRepository.deleteByWxId(wxId) > 0;

    }
}
