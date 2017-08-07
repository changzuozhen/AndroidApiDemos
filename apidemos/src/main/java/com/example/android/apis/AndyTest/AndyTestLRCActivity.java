package com.example.android.apis.AndyTest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.example.android.apis.AndyTest.custom_view.LrcView;
import com.example.android.apis.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import commontools.LogUtils;

public class AndyTestLRCActivity extends Activity {
    private static final String TAG = "AndyTestLRCActivity";
    @BindView(R.id.lrc_view)
    LrcView lrcView;
    @BindView(R.id.seekbar_lrc)
    SeekBar seekbarLrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.andy_test_lrc);
        ButterKnife.bind(this);
        seekbarLrc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtils.d(TAG, "onProgressChanged() called with: " + "progress = [" + progress + "], fromUser = [" + fromUser + "]");
                lrcView.changeCurrent(progress, true);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        lrcView.setLrcStr("[ti:小半]\n[ar:赵小臭]\n[al:   ]\n[by:]\n[offset:0]\n[00:00.03]00:00.03 asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf 小半 - 赵小臭\n[00:01.06]00:01.06 asdfasdf 词：涂玲子\n[00:01.98]00:01.98 asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf 曲：陈粒\n[00:02.86]00:02.86 asdfasdf \n[00:05.59]00:05.59 asdfasdf 不敢回看\n[00:06.59]00:06.59 asdfasdf \n[00:07.59]00:07.59 asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf 左顾右盼不自然的暗自喜欢\n[00:10.93]00:10.93 asdfasdf \n[00:11.84]00:11.84 asdfasdf 偷偷搭讪总没完的坐立难安\n[00:15.41]00:15.41 asdfasdf \n[00:16.30]00:16.30 asdfasdf 试探说晚安 多空泛又心酸\n[00:21.06]00:21.06 asdfasdf \n[00:22.91]00:22.91 asdfasdf 低头呢喃\n[00:24.16]00:24.16 asdfasdf \n[00:25.22]00:25.22 asdfasdf 对你的偏爱太过于明目张胆\n[00:28.53]00:28.53 asdfasdf \n[00:29.34]00:29.34 asdfasdf 在原地打转的小丑伤心不断\n[00:32.87]00:32.87 asdfasdf \n[00:33.73]00:33.73 asdfasdf 空空留遗憾多难堪又为难\n[00:38.48]00:38.48 asdfasdf \n[00:39.71]00:39.71 asdfasdf 释然 慵懒 尽欢\n[00:43.10]00:43.10 asdfasdf 时间风干后你与我再无关\n[00:47.07]00:47.07 asdfasdf \n[00:48.57]00:48.57 asdfasdf 没答案 怎么办\n[00:50.72]00:50.72 asdfasdf 看不惯我自我欺瞒\n[00:54.11]00:54.11 asdfasdf \n[00:56.69]00:56.69 asdfasdf 纵容着 喜欢的 讨厌的\n[00:59.77]00:59.77 asdfasdf 宠溺的 厌倦的\n[01:02.08]01:02.08 asdfasdf 一个个慢慢黯淡\n[01:04.39]01:04.39 asdfasdf \n[01:05.32]01:05.32 asdfasdf 纵容着 任性的 随意的\n[01:08.47]01:08.47 asdfasdf 放肆的 轻易的\n[01:10.64]01:10.64 asdfasdf 将所有欢脱倾翻\n[01:13.10]01:13.10 asdfasdf \n[01:14.00]01:14.00 asdfasdf 不应该 太心软 不大胆\n[01:17.19]01:17.19 asdfasdf 太死板 不果断\n[01:19.47]01:19.47 asdfasdf 玩弄着肆无忌惮\n[01:21.90]01:21.90 asdfasdf \n[01:22.83]01:22.83 asdfasdf 不应该 舍弃了 死心了\n[01:25.87]01:25.87 asdfasdf 放手了 断念了\n[01:28.17]01:28.17 asdfasdf 无可奈何不耐烦\n[01:30.57]01:30.57 asdfasdf \n[01:31.80]01:31.80 asdfasdf 不算\n[01:33.38]01:33.38 asdfasdf \n[01:37.19]01:37.19 asdfasdf 灯火阑珊\n[01:38.28]01:38.28 asdfasdf \n[01:39.39]01:39.39 asdfasdf 我的心借了你的光是明是暗\n[01:42.59]01:42.59 asdfasdf \n[01:43.36]01:43.36 asdfasdf 笑自己情绪太泛滥形只影单\n[01:46.98]01:46.98 asdfasdf \n[01:47.74]01:47.74 asdfasdf 自嘲成习惯 多敏感又难缠\n[01:52.60]01:52.60 asdfasdf \n[01:54.61]01:54.61 asdfasdf 低头呢喃\n[01:55.83]01:55.83 asdfasdf \n[01:56.48]01:56.48 asdfasdf 对你的偏爱太过于明目张胆\n[02:00.10]02:00.10 asdfasdf \n[02:00.83]02:00.83 asdfasdf 在原地打转的小丑伤心不断\n[02:04.42]02:04.42 asdfasdf \n[02:05.18]02:05.18 asdfasdf 空空留遗憾 多难堪又为难\n[02:10.03]02:10.03 asdfasdf \n[02:11.53]02:11.53 asdfasdf 释然 慵懒 尽欢\n[02:14.82]02:14.82 asdfasdf 时间风干后你我再无关\n[02:18.81]02:18.81 asdfasdf \n[02:20.32]02:20.32 asdfasdf 没答案 怎么办\n[02:22.42]02:22.42 asdfasdf 看不惯我自我欺瞒\n[02:26.02]02:26.02 asdfasdf \n[02:28.25]02:28.25 asdfasdf 纵容着 喜欢的 讨厌的\n[02:31.37]02:31.37 asdfasdf 宠溺的 厌倦的\n[02:33.75]02:33.75 asdfasdf 一个个慢慢黯淡\n[02:36.08]02:36.08 asdfasdf \n[02:36.96]02:36.96 asdfasdf 纵容着 任性的 随意的\n[02:40.13]02:40.13 asdfasdf 放肆的 轻易的\n[02:42.29]02:42.29 asdfasdf 将所有欢脱倾翻\n[02:44.82]02:44.82 asdfasdf \n[02:45.70]02:45.70 asdfasdf 不应该 太心软 不大胆\n[02:48.80]02:48.80 asdfasdf 太死板 不果断\n[02:51.00]02:51.00 asdfasdf 玩弄着肆无忌惮\n[02:53.50]02:53.50 asdfasdf \n[02:54.41]02:54.41 asdfasdf 不应该 舍弃了 死心了\n[02:57.56]02:57.56 asdfasdf 放手了 断念了\n[02:59.77]02:59.77 asdfasdf 无可奈何不耐烦\n[03:02.20]03:02.20 asdfasdf \n[03:20.67]03:20.67 asdfasdf 任由着 你躲闪 我追赶\n[03:23.72]03:23.72 asdfasdf 你走散 我呼喊\n[03:25.99]03:25.99 asdfasdf 是谁在泛泛而谈\n[03:28.37]03:28.37 asdfasdf \n[03:29.32]03:29.32 asdfasdf 任由着 你来了 你笑了\n[03:32.50]03:32.50 asdfasdf 你走了 不看我\n[03:34.58]03:34.58 asdfasdf 与理所当然分摊\n[03:37.07]03:37.07 asdfasdf \n[03:38.15]03:38.15 asdfasdf 不明白 残存的 没用的\n[03:41.15]03:41.15 asdfasdf 多余的 不必的\n[03:43.37]03:43.37 asdfasdf 破烂也在手紧攥\n[03:45.92]03:45.92 asdfasdf \n[03:46.72]03:46.72 asdfasdf 不明白 谁赧然 谁无端\n[03:49.94]03:49.94 asdfasdf 谁古板 谁极端\n[03:52.12]03:52.12 asdfasdf 无辜不之所以然\n[03:54.64]03:54.64 asdfasdf \n[03:55.63]03:55.63 asdfasdf 纵容着 喜欢的 讨厌的\n[03:58.65]03:58.65 asdfasdf 宠溺的 厌倦的\n[04:00.74]04:00.74 asdfasdf 一个个慢慢黯淡\n[04:03.27]04:03.27 asdfasdf \n[04:04.16]04:04.16 asdfasdf 纵容着 任性的 随意的\n[04:07.36]04:07.36 asdfasdf 放肆的 轻易的\n[04:09.50]04:09.50 asdfasdf 将所有欢脱倾翻\n[04:12.01]04:12.01 asdfasdf \n[04:12.94]04:12.94 asdfasdf 不应该 太心软 不大胆\n[04:16.09]04:16.09 asdfasdf 太死板 不果断\n[04:18.29]04:18.29 asdfasdf 玩弄着肆无忌惮\n[04:20.75]04:20.75 asdfasdf \n[04:21.63]04:21.63 asdfasdf 不应该 舍弃了 死心了\n[04:24.74]04:24.74 asdfasdf 放手了 断念了\n[04:27.14]04:27.14 asdfasdf 无可奈何不耐烦");
    }
}
