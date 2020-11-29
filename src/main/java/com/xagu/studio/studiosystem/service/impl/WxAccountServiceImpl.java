package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.dao.WxAccountRepository;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import com.xagu.studio.studiosystem.utils.Constants;
import com.xagu.studio.studiosystem.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/28
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Service("wxAccountService")
@Transactional(rollbackFor = RuntimeException.class)
public class WxAccountServiceImpl extends BaseService implements IWxAccountService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    WxAccountRepository wxAccountRepository;


    @Override
    public List<WxAccount> batchAddAccount(String wxAccountStr) {
        String[] accounts = wxAccountStr.trim().split("\\s+");
        List<WxAccount> wxAccounts = new ArrayList<>();
        for (String account : accounts) {
            String[] accountAndPass = account.split("----");
            WxAccount wxAccount;
            //账号----密码
            if (accountAndPass.length == 2) {
                wxAccount = new WxAccount();
            } else if (accountAndPass.length == 3) {
                //账号----密码----a16
                wxAccount = new WxAccount();
                wxAccount.setKey(accountAndPass[2]);
            } else {
                continue;
            }
            wxAccount.setAccount(accountAndPass[0]);
            wxAccount.setPassword(accountAndPass[1]);
            wxAccount.setId(snowFlake.nextId() + "");
            wxAccount.setUpdateTime(new Date());
            wxAccounts.add(wxAccount);
        }
        if (wxAccounts.size() > 0) {
            return wxAccountRepository.saveAll(wxAccounts);
        } else {
            return wxAccounts;
        }
    }

    @Override
    public boolean addWxAccount(WxAccount account) {
        if (StringUtils.isEmpty(account.getAccount())) {
            return false;
        }
        if (StringUtils.isEmpty(account.getPassword())) {
            return false;
        }
        account.setId(snowFlake.nextId() + "");
        account.setUpdateTime(new Date());
        wxAccountRepository.save(account);
        return true;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.SERIALIZABLE)
    public WxAccount getAccount(String imei) {
        WxAccount oneByImei = wxAccountRepository.findOneByImei(imei);
        if (oneByImei != null) {
            return oneByImei;
        } else {
            WxAccount wxAccount = wxAccountRepository.findFirstByStatusEqualsOrderByUpdateTimeAsc(Constants.WxAccountStatus.NOT_USED);
            if (wxAccount != null) {
                wxAccount.setImei(imei);
                wxAccount.setStatus(Constants.WxAccountStatus.USED);
                wxAccountRepository.save(wxAccount);
            }
            return wxAccount;
        }
    }

    @Override
    public List<WxAccount> getNotUsedAccount() {
        return wxAccountRepository.findByStatusEquals(Constants.WxAccountStatus.NOT_USED);
    }

    @Override
    public List<WxAccount> getUsedAccount() {
        return wxAccountRepository.findByStatusNot(Constants.WxAccountStatus.NOT_USED);
    }

    @Override
    public boolean clearAccount() {
        wxAccountRepository.deleteAll();
        return true;
    }

    @Override
    public boolean deleteAccount(String[] ids) {
        return wxAccountRepository.deleteWxAccountsByIdIn(ids) > 0;
    }

    @Override
    public boolean updateAccount(WxAccount wxAccount) {
        String id = wxAccount.getId();
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        WxAccount dbWxAccount = wxAccountRepository.findOneById(id);
        if (dbWxAccount == null) {
            return false;
        }
        String account = wxAccount.getAccount();
        if (!StringUtils.isEmpty(account)) {
            dbWxAccount.setAccount(account);
        }
        String password = wxAccount.getPassword();
        if (!StringUtils.isEmpty(password)) {
            dbWxAccount.setPassword(password);
        }
        String key = wxAccount.getKey();
        if (!StringUtils.isEmpty(key)) {
            dbWxAccount.setKey(key);
        }
        String imei = wxAccount.getImei();
        if (!StringUtils.isEmpty(imei)) {
            dbWxAccount.setImei(imei);
        }
        String status = wxAccount.getStatus();
        if (!StringUtils.isEmpty(status)) {
            dbWxAccount.setStatus(status);
        }
        dbWxAccount.setUpdateTime(new Date());
        wxAccountRepository.save(dbWxAccount);
        return true;
    }

    @Override
    public WxAccount getAccountById(String id) {
        return wxAccountRepository.findOneById(id);
    }

    @Override
    public Page<WxAccount> listAccount(Integer page, Integer size, String account, String imei, String status, String id) {
        page = this.checkPage(page);
        size = this.checkSize(size);
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        //开始查询
        Page<WxAccount> wxAccounts = wxAccountRepository.findAll(new Specification<WxAccount>() {
            @Override
            public Predicate toPredicate(Root<WxAccount> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(id)) {
                    predicates.add(criteriaBuilder.like(root.get("id").as(String.class), id + "%"));
                }
                if (!StringUtils.isEmpty(account)) {
                    predicates.add(criteriaBuilder.like(root.get("account").as(String.class), account + "%"));
                }
                if (!StringUtils.isEmpty(imei)) {
                    predicates.add(criteriaBuilder.like(root.get("imei"), imei + "%"));
                }
                if (!StringUtils.isEmpty(status)) {
                    predicates.add(criteriaBuilder.equal(root.get("status"), status));
                }
                Predicate[] predicate = new Predicate[predicates.size()];
                predicates.toArray(predicate);
                return criteriaBuilder.and(predicate);
            }
        }, pageable);
        return wxAccounts;
    }
}
