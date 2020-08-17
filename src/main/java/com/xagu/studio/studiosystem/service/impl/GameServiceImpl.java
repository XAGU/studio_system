package com.xagu.studio.studiosystem.service.impl;

import com.xagu.studio.studiosystem.bean.Game;
import com.xagu.studio.studiosystem.bean.GameScript;
import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.dao.GameRepository;
import com.xagu.studio.studiosystem.dao.GameScriptRepository;
import com.xagu.studio.studiosystem.dao.WxAccountRepository;
import com.xagu.studio.studiosystem.service.IGameScriptService;
import com.xagu.studio.studiosystem.service.IGameService;
import com.xagu.studio.studiosystem.utils.Constants;
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

import javax.annotation.Resource;
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
@Service("gameService")
@Transactional(rollbackFor = RuntimeException.class)
public class GameServiceImpl extends BaseService implements IGameService {

    @Autowired
    SnowFlake snowFlake;

    @Resource
    GameRepository gameRepository;

    @Resource
    WxAccountRepository wxAccountRepository;


    @Resource
    IGameScriptService gameScriptService;

    @Resource
    GameScriptRepository gameScriptRepository;

    @Override
    public boolean addGame(Game game) {
        game.setId(snowFlake.nextId() + "");
        game.setUpdateTime(new Date());
        gameRepository.save(game);
        return true;
    }

    @Override
    public boolean deleteGame(String[] ids) {
        for (String id : ids) {
            Game oneById = gameRepository.findOneById(id);
            String scriptId = oneById.getScriptId();
            if (scriptId != null) {
                gameScriptService.deleteGameScript(scriptId);
            }
        }
        return gameRepository.deleteGamesByIdIn(ids) > 0;
    }

    @Override
    public boolean updateGame(Game game) {
        String id = game.getId();
        if (StringUtils.isEmpty(id)) {
            return false;
        }
        Game dbGame = gameRepository.findOneById(id);
        if (dbGame == null) {
            return false;
        }
        String loginedSign = game.getLoginedSign();
        if (!StringUtils.isEmpty(loginedSign)) {
            dbGame.setLoginedSign(loginedSign);
        }
        String loginSign = game.getLoginSign();
        if (!StringUtils.isEmpty(loginSign)) {
            dbGame.setLoginSign(loginSign);
        }
        dbGame.setUpdateTime(new Date());
        gameRepository.save(dbGame);
        return true;
    }

    @Override
    public Game getGameByid(String id) {
        return gameRepository.findOneById(id);
    }

    @Override
    public Page<Game> listGame(Integer page, Integer size, String gameName, String type) {
        page = this.checkPage(page);
        size = this.checkSize(size);
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        //开始查询
        Page<Game> games = gameRepository.findAll(new Specification<Game>() {
            @Override
            public Predicate toPredicate(Root<Game> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(gameName)) {
                    predicates.add(criteriaBuilder.like(root.get("gameName").as(String.class), "%" + gameName + "%"));
                }
                if (!StringUtils.isEmpty(type)) {
                    predicates.add(criteriaBuilder.equal(root.get("type"), type));
                }
                Predicate[] predicate = new Predicate[predicates.size()];
                predicates.toArray(predicate);
                return criteriaBuilder.and(predicate);
            }
        }, pageable);
        return games;
    }

    @Override
    public Game randomGame(String imei) {
        WxAccount wxAccount = wxAccountRepository.findOneByImei(imei);
        if (wxAccount == null) {
            return null;
        }
        Game game = gameRepository.randomGame(wxAccount.getId(), Constants.GameType.RIGHT_NOW_RUN);
        if (game != null) {
            return game;
        }
        game = gameRepository.randomGame(wxAccount.getId(), Constants.GameType.AFTER_RUN);
        return game;
    }

    @Override
    public boolean setGameOver(String wxId, String gameId) {
        return gameRepository.setGameOver(wxId, gameId) > 0;
    }

    @Override
    public boolean setGameScript(String gameId, String scriptId) {
        Game dbGame = gameRepository.findOneById(gameId);
        if (dbGame == null) {
            return false;
        }
        GameScript dbScript = gameScriptRepository.findOneById(scriptId);
        if (dbScript == null) {
            return false;
        }
        dbGame.setScriptId(scriptId);
        gameRepository.save(dbGame);
        return true;
    }
}
