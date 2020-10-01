package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.bean.ZqAccount;
import com.xagu.studio.studiosystem.dao.WxAccountRepository;
import com.xagu.studio.studiosystem.dao.ZqAccountRepository;
import com.xagu.studio.studiosystem.service.IZqAccountService;
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
@Service("zqAccountService")
@Transactional(rollbackFor = RuntimeException.class)
public class ZqAccountServiceImpl extends BaseService implements IZqAccountService {

    @Autowired
    SnowFlake snowFlake;

    @Autowired
    ZqAccountRepository zqAccountRepository;

    @Autowired
    WxAccountRepository wxAccountRepository;

    @Override
    public boolean addZqAccount(ZqAccount zqAccount, String imei) {
        if (StringUtils.isEmpty(zqAccount.getAccountId())) {
            return false;
        }
        if (StringUtils.isEmpty(zqAccount.getPassword())) {
            return false;
        }
        if (StringUtils.isEmpty(zqAccount.getPhone())) {
            return false;
        }
        if (StringUtils.isEmpty(zqAccount.getToken())) {
            return false;
        }
        if (StringUtils.isEmpty(zqAccount.getWxId())) {
            if (StringUtils.isEmpty(imei)) {
                return false;
            } else {
                WxAccount wxAccount = wxAccountRepository.findOneByImei(imei);
                if (wxAccount != null) {
                    zqAccount.setWxId(wxAccount.getId());
                } else {
                    return false;
                }
            }
        } else {
            WxAccount wxAccount = wxAccountRepository.findOneById(zqAccount.getWxId());
            if (wxAccount == null) {
                return false;
            }
        }
        zqAccount.setId(snowFlake.nextId() + "");
        zqAccount.setRegTime(new Date());
        zqAccountRepository.save(zqAccount);
        return true;
    }

    @Override
    public boolean deleteAccount(String[] ids) {
        return zqAccountRepository.deleteZqAccountsByIdIn(ids) > 0;
    }

    @Override
    public ZqAccount getAccountById(String id) {
        return zqAccountRepository.findOneById(id);
    }

    @Override
    public Page<ZqAccount> listAccount(Integer page, Integer size, String phone, String wxId) {
        page = this.checkPage(page);
        size = this.checkSize(size);
        Sort sort = Sort.by(Sort.Direction.DESC, "regTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        //开始查询
        Page<ZqAccount> zqAccounts = zqAccountRepository.findAll(new Specification<ZqAccount>() {
            @Override
            public Predicate toPredicate(Root<ZqAccount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(phone)) {
                    predicates.add(criteriaBuilder.like(root.get("phone").as(String.class), phone + "%"));
                }
                if (!StringUtils.isEmpty(wxId)) {
                    predicates.add(criteriaBuilder.like(root.get("wxId").as(String.class), wxId));
                }
                Predicate[] predicate = new Predicate[predicates.size()];
                predicates.toArray(predicate);
                return criteriaBuilder.and(predicate);
            }
        }, pageable);
        return zqAccounts;
    }

    @Override
    public boolean setGotMoney(String id, String money) {
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        ZqAccount dbZqAccount = zqAccountRepository.findOneById(id);
        if (dbZqAccount == null) {
            return false;
        }
        if (StringUtils.isEmpty(money)) {
            return false;
        }
        double addMoney = Double.parseDouble(money);
        double originMoney = Double.parseDouble(dbZqAccount.getMoney() != null ? dbZqAccount.getMoney() : "0");
        dbZqAccount.setMoney(String.valueOf(addMoney + originMoney));
        return true;
    }

    @Override
    public ZqAccount getAccountByImei(String imei) {
        WxAccount wxAccount = wxAccountRepository.findOneByImei(imei);
        if (wxAccount == null) {
            return null;
        }
        return zqAccountRepository.findOneByWxId(wxAccount.getId());
    }
}
