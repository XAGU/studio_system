package com.xagu.studio.studiosystem.controller;

import com.xagu.studio.studiosystem.bean.Game;
import com.xagu.studio.studiosystem.bean.WxAccount;
import com.xagu.studio.studiosystem.response.ResponseResult;
import com.xagu.studio.studiosystem.service.IGameService;
import com.xagu.studio.studiosystem.service.IWxAccountService;
import com.xagu.studio.studiosystem.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xagu
 * Created on 2020/7/28
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@RestController
@RequestMapping("game")
public class GameController {

    @Resource
    IGameService gameService;


    /**
     * 添加游戏
     *
     * @param game
     * @return
     */
    @PostMapping("addGame")
    public ResponseResult addGame(Game game) {
        if (StringUtils.isEmpty(game.getGameName())) {
            return ResponseResult.FAILED("游戏名不能为空！");
        }
        if (StringUtils.isEmpty(game.getAdid())) {
            return ResponseResult.FAILED("adid不能为空！");
        }
        if (StringUtils.isEmpty(game.getSadid())) {
            return ResponseResult.FAILED("sadid不能为空！");
        }
        if (StringUtils.isEmpty(game.getPackageName())) {
            return ResponseResult.FAILED("包名不能为空！");
        }
        String type = game.getType();
        if (StringUtils.isEmpty(type) || (!type.equals(Constants.GameType.AFTER_RUN) && !type.equals(Constants.GameType.RIGHT_NOW_RUN))) {
            return ResponseResult.FAILED("类型错误！");
        }
        return ResponseResult.decide(gameService.addGame(game),
                "添加游戏成功",
                "添加游戏失败");
    }

    @PostMapping("deleteGame")
    public ResponseResult deleteAccount(String ids) {
        return ResponseResult.decide(gameService.deleteGame(ids.split(",")),
                "删除游戏成功",
                "删除游戏失败");

    }

    @PostMapping("updateGame")
    public ResponseResult updateAccount(Game game) {
        return ResponseResult.decide(gameService.updateGame(game),
                "更新游戏信息成功",
                "更新游戏信息失败");
    }

    @GetMapping("getGameById")
    public ResponseResult getAccountById(String id) {
        Game gameByid = gameService.getGameByid(id);
        return ResponseResult.decide(gameByid != null,
                "获取游戏成功",
                "获取游戏失败")
                .setData(gameByid);
    }

    @GetMapping("listGame")
    public ResponseResult listGame(Integer page, Integer size, String gameName, String type) {
        Page<Game> games = gameService.listGame(page, size, gameName, type);
        return ResponseResult.SUCCESS("获取游戏成功").setData(games);
    }

    @GetMapping("listAfterRunGame")
    public ResponseResult listAfterRunGame() {
        Page<Game> games = gameService.listGame(1, 100, null, Constants.GameType.AFTER_RUN);
        return ResponseResult.SUCCESS("获取游戏成功").setData(games);
    }

    @GetMapping("randomGame")
    public ResponseResult randomGame(String imei) {
        if (StringUtils.isEmpty(imei)) {
            return ResponseResult.FAILED("imei设备号不能为空！");
        }
        Game game = gameService.randomGame(imei);
        return ResponseResult.decide(game != null,
                "获取随机脚本成功!",
                "所有脚本均已运行，无更多脚本!")
                .setData(game);
    }

    @PostMapping("setGameOver")
    public ResponseResult setGameOver(String wxId, String gameId) {
        return ResponseResult.decide(gameService.setGameOver(wxId, gameId),
                "已同步脚本运行状态",
                "同步脚本运行状态失败");
    }

    @PostMapping("setGameScript")
    public ResponseResult setGameScript(String gameId, String scriptId) {
        if (StringUtils.isEmpty(gameId)){
            return ResponseResult.FAILED("游戏id不能为空");
        }
        if (StringUtils.isEmpty(scriptId)){
            return ResponseResult.FAILED("脚本id不能为空");
        }
        return ResponseResult.decide(gameService.setGameScript(gameId, scriptId),
                "添加游戏脚本成功",
                "添加游戏脚本失败");
    }

}
