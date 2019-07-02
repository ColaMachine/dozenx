
<template>

      <section id="comments">
                  <div class="panelTitle" id="panelTitle">评论<span class="greyNum">（<em class="commentNum">41</em>）</span></div>
                      <div class="comment_sendwrap noLogin">
                          <div id="hidden_vote_div" style="display:none;"></div>
                      <p id="comment_error" class="red"></p>
              <div class="comment_avatar">
                  <span class="userPic"><img :src="isLogin?loginUser.face:'//res.smzdm.com/images/header/default_small.png'" id="comment_avatar" width="60" height="60"></span>
              </div>
              <div class="comment_sendPart">
                  <form id="commentform" class="commentform" onsubmit="return false;" method="post" action="">
                      <input type="hidden" id="type" name="type" class="quickCommentType" value="3">
                      <input type="hidden" id="pid" name="pid" value="14039324">
                      <input type="hidden" id="parentid" name="parentid" value="0">
                      <input type="hidden" id="vote_id" name="vote_id" value="0">
                      <input type="hidden" id="vote_type" name="vote_type" value="">
                      <input type="hidden" id="vote_group" name="vote_group" value="">
                      <div v-if="!isLogin" class="comment_tips zhiyou_login">发表评论请 <a @click="goLogin" href="javascript:void(0);">登录</a></div>
                      <textarea cols="70" rows="3" ref="pinglun" name="content" class="textarea_comment zhiyou_login" id="textareaComment" default_data="欢迎对文章中提到的商品或价格进行点评，具备参考价值的评论对别人更有帮助。"></textarea>
                      <div id="loading" style="display: none;"><img style="vertical-align:middle;" src="//www.smzdm.com/resources/public/img/loading_comment.gif"> </div>
                      <div id="error" style="display:none;"> </div>
                      <div class="comment_operate">
                          <div class="comment_smileBox comment_switch">
                              <i class="icon-small"><!--[if lt IE 8]>表情<![endif]--></i>
                              <div class="smileLayerBg" id="smiley" style="display:none;">
                              <ul class="smileBox"><li><a href="javascript:void(0)" class="smile147" default-data="[吃土]" title="吃土"></a></li><li><a href="javascript:void(0)" class="smile148" default-data="[大白菜]" title="大白菜"></a></li><li><a href="javascript:void(0)" class="smile149" default-data="[大妈三宝]" title="大妈三宝"></a></li><li><a href="javascript:void(0)" class="smile150" default-data="[解毒]" title="解毒"></a></li><li><a href="javascript:void(0)" class="smile151" default-data="[领券防身]" title="领券防身"></a></li><li><a href="javascript:void(0)" class="smile152" default-data="[榴莲]" title="榴莲"></a></li><li><a href="javascript:void(0)" class="smile153" default-data="[买买买]" title="买买买"></a></li><li><a href="javascript:void(0)" class="smile154" default-data="[钱包被掏空]" title="钱包被掏空"></a></li><li><a href="javascript:void(0)" class="smile155" default-data="[让你买]" title="让你买"></a></li><li><a href="javascript:void(0)" class="smile156" default-data="[神价格]" title="神价格"></a></li><li><a href="javascript:void(0)" class="smile157" default-data="[手慢无]" title="手慢无"></a></li><li><a href="javascript:void(0)" class="smile158" default-data="[损失几个亿]" title="损失几个亿"></a></li><li><a href="javascript:void(0)" class="smile159" default-data="[铁茄子]" title="铁茄子"></a></li><li><a href="javascript:void(0)" class="smile160" default-data="[吐血推荐]" title="吐血推荐"></a></li><li><a href="javascript:void(0)" class="smile161" default-data="[种草]" title="种草"></a></li><li><a href="javascript:void(0)" class="smile162" default-data="[紫薯布丁]" title="紫薯布丁"></a></li><li><a href="javascript:void(0)" class="smile163" default-data="[doge]" title="doge"></a></li><li><a href="javascript:void(0)" class="smile164" default-data="[ok了]" title="ok了"></a></li><li><a href="javascript:void(0)" class="smile165" default-data="[棒棒哒]" title="棒棒哒"></a></li><li><a href="javascript:void(0)" class="smile166" default-data="[捂嘴]" title="捂嘴"></a></li><li><a href="javascript:void(0)" class="smile167" default-data="[吃]" title="吃"></a></li><li><a href="javascript:void(0)" class="smile168" default-data="[吃药]" title="吃药"></a></li><li><a href="javascript:void(0)" class="smile169" default-data="[大哭]" title="大哭"></a></li><li><a href="javascript:void(0)" class="smile170" default-data="[好冷啊]" title="好冷啊"></a></li><li><a href="javascript:void(0)" class="smile171" default-data="[鬼脸]" title="鬼脸"></a></li><li><a href="javascript:void(0)" class="smile172" default-data="[害羞了]" title="害羞了"></a></li><li><a href="javascript:void(0)" class="smile173" default-data="[嘿嘿]" title="嘿嘿"></a></li><li><a href="javascript:void(0)" class="smile174" default-data="[花痴]" title="花痴"></a></li><li><a href="javascript:void(0)" class="smile175" default-data="[被吓到]" title="被吓到"></a></li><li><a href="javascript:void(0)" class="smile176" default-data="[很惊讶]" title="很惊讶"></a></li><li><a href="javascript:void(0)" class="smile177" default-data="[开花]" title="开花"></a></li><li><a href="javascript:void(0)" class="smile178" default-data="[开心]" title="开心"></a></li><li><a href="javascript:void(0)" class="smile179" default-data="[看不到]" title="看不到"></a></li><li><a href="javascript:void(0)" class="smile180" default-data="[抠鼻孔]" title="抠鼻孔"></a></li><li><a href="javascript:void(0)" class="smile181" default-data="[么么哒]" title="么么哒"></a></li><li><a href="javascript:void(0)" class="smile182" default-data="[难过]" title="难过"></a></li><li><a href="javascript:void(0)" class="smile183" default-data="[生气]" title="生气"></a></li><li><a href="javascript:void(0)" class="smile184" default-data="[唔]" title="唔"></a></li><li><a href="javascript:void(0)" class="smile185" default-data="[笑]" title="笑"></a></li><li><a href="javascript:void(0)" class="smile186" default-data="[耶]" title="耶"></a></li><li><a href="javascript:void(0)" class="smile187" default-data="[疑问]" title="疑问"></a></li><li><a href="javascript:void(0)" class="smile188" default-data="[晕倒]" title="晕倒"></a></li><li><a href="javascript:void(0)" class="smile189" default-data="[再见]" title="再见"></a></li><li><a href="javascript:void(0)" class="smile190" default-data="[赞]" title="赞"></a></li></ul><ul class="smileBox" style="display:none;"><li><a href="javascript:void(0)" class="smile23" default-data="[高兴]" title="高兴"></a></li><li><a href="javascript:void(0)" class="smile24" default-data="[皱眉]" title="皱眉"></a></li><li><a href="javascript:void(0)" class="smile25" default-data="[阴笑]" title="阴笑"></a></li><li><a href="javascript:void(0)" class="smile26" default-data="[鄙视]" title="鄙视"></a></li><li><a href="javascript:void(0)" class="smile27" default-data="[邪恶]" title="邪恶"></a></li><li><a href="javascript:void(0)" class="smile28" default-data="[大囧]" title="大囧"></a></li><li><a href="javascript:void(0)" class="smile29" default-data="[惊喜]" title="惊喜"></a></li><li><a href="javascript:void(0)" class="smile30" default-data="[小眼睛]" title="小眼睛"></a></li><li><a href="javascript:void(0)" class="smile31" default-data="[小怒]" title="小怒"></a></li><li><a href="javascript:void(0)" class="smile32" default-data="[无语]" title="无语"></a></li><li><a href="javascript:void(0)" class="smile33" default-data="[傻笑]" title="傻笑"></a></li><li><a href="javascript:void(0)" class="smile34" default-data="[期待]" title="期待"></a></li><li><a href="javascript:void(0)" class="smile35" default-data="[得逞]" title="得逞"></a></li><li><a href="javascript:void(0)" class="smile36" default-data="[呲牙]" title="呲牙"></a></li><li><a href="javascript:void(0)" class="smile37" default-data="[喜极而泣]" title="喜极而泣"></a></li><li><a href="javascript:void(0)" class="smile38" default-data="[晕死]" title="晕死"></a></li><li><a href="javascript:void(0)" class="smile39" default-data="[脸红]" title="脸红"></a></li><li><a href="javascript:void(0)" class="smile40" default-data="[亲亲]" title="亲亲"></a></li><li><a href="javascript:void(0)" class="smile41" default-data="[狂汗]" title="狂汗"></a></li><li><a href="javascript:void(0)" class="smile42" default-data="[困]" title="困"></a></li><li><a href="javascript:void(0)" class="smile43" default-data="[饥饿]" title="饥饿"></a></li><li><a href="javascript:void(0)" class="smile44" default-data="[得意]" title="得意"></a></li><li><a href="javascript:void(0)" class="smile45" default-data="[抠鼻]" title="抠鼻"></a></li><li><a href="javascript:void(0)" class="smile46" default-data="[抽烟]" title="抽烟"></a></li><li><a href="javascript:void(0)" class="smile47" default-data="[内伤]" title="内伤"></a></li><li><a href="javascript:void(0)" class="smile48" default-data="[口水]" title="口水"></a></li><li><a href="javascript:void(0)" class="smile49" default-data="[吐]" title="吐"></a></li><li><a href="javascript:void(0)" class="smile50" default-data="[流鼻涕]" title="流鼻涕"></a></li><li><a href="javascript:void(0)" class="smile51" default-data="[绷带]" title="绷带"></a></li><li><a href="javascript:void(0)" class="smile52" default-data="[吐舌]" title="吐舌"></a></li><li><a href="javascript:void(0)" class="smile53" default-data="[口罩]" title="口罩"></a></li><li><a href="javascript:void(0)" class="smile54" default-data="[不说话]" title="不说话"></a></li><li><a href="javascript:void(0)" class="smile55" default-data="[不出所料]" title="不出所料"></a></li><li><a href="javascript:void(0)" class="smile56" default-data="[装大款]" title="装大款"></a></li><li><a href="javascript:void(0)" class="smile57" default-data="[尴尬]" title="尴尬"></a></li><li><a href="javascript:void(0)" class="smile58" default-data="[喷水]" title="喷水"></a></li><li><a href="javascript:void(0)" class="smile59" default-data="[喷血]" title="喷血"></a></li><li><a href="javascript:void(0)" class="smile60" default-data="[咽气]" title="咽气"></a></li><li><a href="javascript:void(0)" class="smile61" default-data="[哼小曲]" title="哼小曲"></a></li><li><a href="javascript:void(0)" class="smile62" default-data="[吹泡泡]" title="吹泡泡"></a></li><li><a href="javascript:void(0)" class="smile63" default-data="[愤怒]" title="愤怒"></a></li><li><a href="javascript:void(0)" class="smile64" default-data="[冻住]" title="冻住"></a></li><li><a href="javascript:void(0)" class="smile65" default-data="[赞一个]" title="赞一个"></a></li><li><a href="javascript:void(0)" class="smile66" default-data="[差评]" title="差评"></a></li></ul><ul class="smileBox" style="display:none;"><li><a href="javascript:void(0)" class="smile67" default-data="[中指]" title="中指"></a></li><li><a href="javascript:void(0)" class="smile68" default-data="[哦耶]" title="哦耶"></a></li><li><a href="javascript:void(0)" class="smile69" default-data="[拳头]" title="拳头"></a></li><li><a href="javascript:void(0)" class="smile70" default-data="[手掌]" title="手掌"></a></li><li><a href="javascript:void(0)" class="smile71" default-data="[就是你]" title="就是你"></a></li><li><a href="javascript:void(0)" class="smile72" default-data="[过来]" title="过来"></a></li><li><a href="javascript:void(0)" class="smile73" default-data="[吃雪糕]" title="吃雪糕"></a></li><li><a href="javascript:void(0)" class="smile74" default-data="[吃香蕉]" title="吃香蕉"></a></li><li><a href="javascript:void(0)" class="smile75" default-data="[ok]" title="ok"></a></li><li><a href="javascript:void(0)" class="smile76" default-data="[看不见]" title="看不见"></a></li><li><a href="javascript:void(0)" class="smile77" default-data="[无所谓]" title="无所谓"></a></li><li><a href="javascript:void(0)" class="smile78" default-data="[欢呼]" title="欢呼"></a></li><li><a href="javascript:void(0)" class="smile79" default-data="[偷笑]" title="偷笑"></a></li><li><a href="javascript:void(0)" class="smile80" default-data="[无奈]" title="无奈"></a></li><li><a href="javascript:void(0)" class="smile81" default-data="[害羞]" title="害羞"></a></li><li><a href="javascript:void(0)" class="smile82" default-data="[打脸]" title="打脸"></a></li><li><a href="javascript:void(0)" class="smile83" default-data="[想一想]" title="想一想"></a></li><li><a href="javascript:void(0)" class="smile84" default-data="[嘘]" title="嘘"></a></li><li><a href="javascript:void(0)" class="smile85" default-data="[烧香]" title="烧香"></a></li><li><a href="javascript:void(0)" class="smile86" default-data="[抱拳]" title="抱拳"></a></li><li><a href="javascript:void(0)" class="smile87" default-data="[鼓掌]" title="鼓掌"></a></li><li><a href="javascript:void(0)" class="smile88" default-data="[观察]" title="观察"></a></li><li><a href="javascript:void(0)" class="smile89" default-data="[锁眉]" title="锁眉"></a></li><li><a href="javascript:void(0)" class="smile90" default-data="[黑线]" title="黑线"></a></li><li><a href="javascript:void(0)" class="smile91" default-data="[汗]" title="汗"></a></li><li><a href="javascript:void(0)" class="smile92" default-data="[哭泣]" title="哭泣"></a></li><li><a href="javascript:void(0)" class="smile93" default-data="[阴暗]" title="阴暗"></a></li><li><a href="javascript:void(0)" class="smile94" default-data="[暗地观察]" title="暗地观察"></a></li><li><a href="javascript:void(0)" class="smile95" default-data="[潜水]" title="潜水"></a></li><li><a href="javascript:void(0)" class="smile96" default-data="[蜡烛]" title="蜡烛"></a></li><li><a href="javascript:void(0)" class="smile97" default-data="[投降]" title="投降"></a></li><li><a href="javascript:void(0)" class="smile98" default-data="[吐血倒地]" title="吐血倒地"></a></li><li><a href="javascript:void(0)" class="smile99" default-data="[便便]" title="便便"></a></li><li><a href="javascript:void(0)" class="smile100" default-data="[长草]" title="长草"></a></li><li><a href="javascript:void(0)" class="smile101" default-data="[绿帽子]" title="绿帽子"></a></li><li><a href="javascript:void(0)" class="smile102" default-data="[注意安全]" title="注意安全"></a></li><li><a href="javascript:void(0)" class="smile103" default-data="[被砍]" title="被砍"></a></li><li><a href="javascript:void(0)" class="smile104" default-data="[捶打]" title="捶打"></a></li><li><a href="javascript:void(0)" class="smile105" default-data="[肿包]" title="肿包"></a></li><li><a href="javascript:void(0)" class="smile106" default-data="[坐等]" title="坐等"></a></li><li><a href="javascript:void(0)" class="smile107" default-data="[看热闹]" title="看热闹"></a></li><li><a href="javascript:void(0)" class="smile108" default-data="[深思]" title="深思"></a></li><li><a href="javascript:void(0)" class="smile109" default-data="[被监视]" title="被监视"></a></li><li><a href="javascript:void(0)" class="smile110" default-data="[探头]" title="探头"></a></li></ul><ul class="smileBox" style="display:none;"><li><a href="javascript:void(0)" class="smile111" default-data="[看看]" title="看看"></a></li><li><a href="javascript:void(0)" class="smile112" default-data="[嘿嘿嘿]" title="嘿嘿嘿"></a></li><li><a href="javascript:void(0)" class="smile113" default-data="[睁大眼睛]" title="睁大眼睛"></a></li><li><a href="javascript:void(0)" class="smile114" default-data="[伸舌头]" title="伸舌头"></a></li><li><a href="javascript:void(0)" class="smile115" default-data="[吃骨头]" title="吃骨头"></a></li><li><a href="javascript:void(0)" class="smile116" default-data="[摸摸头]" title="摸摸头"></a></li><li><a href="javascript:void(0)" class="smile117" default-data="[被咬了]" title="被咬了"></a></li><li><a href="javascript:void(0)" class="smile118" default-data="[献花]" title="献花"></a></li><li><a href="javascript:void(0)" class="smile119" default-data="[献黄瓜]" title="献黄瓜"></a></li><li><a href="javascript:void(0)" class="smile120" default-data="[献香蕉]" title="献香蕉"></a></li><li><a href="javascript:void(0)" class="smile121" default-data="[举个栗子]" title="举个栗子"></a></li><li><a href="javascript:void(0)" class="smile122" default-data="[该吃药了]" title="该吃药了"></a></li><li><a href="javascript:void(0)" class="smile123" default-data="[卫生纸]" title="卫生纸"></a></li><li><a href="javascript:void(0)" class="smile124" default-data="[＋1]" title="＋1"></a></li><li><a href="javascript:void(0)" class="smile125" default-data="[烧钱]" title="烧钱"></a></li><li><a href="javascript:void(0)" class="smile126" default-data="[有钱任性]" title="有钱任性"></a></li><li><a href="javascript:void(0)" class="smile127" default-data="[丢骨头]" title="丢骨头"></a></li><li><a href="javascript:void(0)" class="smile128" default-data="[丢手雷]" title="丢手雷"></a></li><li><a href="javascript:void(0)" class="smile129" default-data="[丢肥皂]" title="丢肥皂"></a></li><li><a href="javascript:void(0)" class="smile130" default-data="[飞吻]" title="飞吻"></a></li><li><a href="javascript:void(0)" class="smile131" default-data="[钻被窝]" title="钻被窝"></a></li><li><a href="javascript:void(0)" class="smile132" default-data="[谋杀]" title="谋杀"></a></li><li><a href="javascript:void(0)" class="smile133" default-data="[大宝剑]" title="大宝剑"></a></li><li><a href="javascript:void(0)" class="smile134" default-data="[手枪]" title="手枪"></a></li><li><a href="javascript:void(0)" class="smile135" default-data="[鞭子]" title="鞭子"></a></li><li><a href="javascript:void(0)" class="smile136" default-data="[炸药包]" title="炸药包"></a></li><li><a href="javascript:void(0)" class="smile137" default-data="[自杀]" title="自杀"></a></li><li><a href="javascript:void(0)" class="smile138" default-data="[中枪]" title="中枪"></a></li><li><a href="javascript:void(0)" class="smile139" default-data="[击掌]" title="击掌"></a></li><li><a href="javascript:void(0)" class="smile140" default-data="[扇耳光]" title="扇耳光"></a></li><li><a href="javascript:void(0)" class="smile141" default-data="[砸钱]" title="砸钱"></a></li><li><a href="javascript:void(0)" class="smile142" default-data="[抽打]" title="抽打"></a></li><li><a href="javascript:void(0)" class="smile143" default-data="[相爱相杀]" title="相爱相杀"></a></li><li><a href="javascript:void(0)" class="smile144" default-data="[斩杀]" title="斩杀"></a></li><li><a href="javascript:void(0)" class="smile145" default-data="[背刺]" title="背刺"></a></li><li><a href="javascript:void(0)" class="smile146" default-data="[中刀]" title="中刀"></a></li></ul><ul class="smileBox" style="display:none;"><li><a href="javascript:void(0)" class="smile1" default-data="[糗大了]" title="糗大了"></a></li><li><a href="javascript:void(0)" class="smile2" default-data="[示爱]" title="示爱"></a></li><li><a href="javascript:void(0)" class="smile3" default-data="[晕]" title="晕"></a></li><li><a href="javascript:void(0)" class="smile4" default-data="[酷]" title="酷"></a></li><li><a href="javascript:void(0)" class="smile5" default-data="[流泪]" title="流泪"></a></li><li><a href="javascript:void(0)" class="smile6" default-data="[饿了]" title="饿了"></a></li><li><a href="javascript:void(0)" class="smile7" default-data="[闭嘴]" title="闭嘴"></a></li><li><a href="javascript:void(0)" class="smile8" default-data="[做鬼脸]" title="做鬼脸"></a></li><li><a href="javascript:void(0)" class="smile9" default-data="[馋]" title="馋"></a></li><li><a href="javascript:void(0)" class="smile10" default-data="[坏笑]" title="坏笑"></a></li><li><a href="javascript:void(0)" class="smile11" default-data="[抓狂]" title="抓狂"></a></li><li><a href="javascript:void(0)" class="smile12" default-data="[呵呵]" title="呵呵"></a></li><li><a href="javascript:void(0)" class="smile13" default-data="[淡定]" title="淡定"></a></li><li><a href="javascript:void(0)" class="smile14" default-data="[冷汗]" title="冷汗"></a></li><li><a href="javascript:void(0)" class="smile15" default-data="[色]" title="色"></a></li><li><a href="javascript:void(0)" class="smile16" default-data="[惊讶]" title="惊讶"></a></li><li><a href="javascript:void(0)" class="smile17" default-data="[希望]" title="希望"></a></li><li><a href="javascript:void(0)" class="smile18" default-data="[伤心]" title="伤心"></a></li><li><a href="javascript:void(0)" class="smile19" default-data="[微笑]" title="微笑"></a></li><li><a href="javascript:void(0)" class="smile20" default-data="[惊吓]" title="惊吓"></a></li><li><a href="javascript:void(0)" class="smile21" default-data="[哈哈]" title="哈哈"></a></li><li><a href="javascript:void(0)" class="smile22" default-data="[吃饭]" title="吃饭"></a></li></ul><div class="smilePage"><a href="javascript:void(0)" class="current">1</a><a href="javascript:void(0)">2</a><a href="javascript:void(0)">3</a><a href="javascript:void(0)">4</a><a href="javascript:void(0)">5</a></div></div>
                          </div>
                          <div class="comment_share">
                              <button type="submit" @click="submitComment" :class="getComputedCls" id="textCommentSubmit">提交</button>
                                                  </div>
                      </div>
                  </form>
              </div>
              <div class="clear"></div>
          </div>

                  <div class="comment_wrap" id="comment">
              <ul class="tab_nav">
                  <li class="tab_comment_li current_item"><span>最新</span></li>
                  <li class="tab_comment_li"><span>最热</span></li>
              </ul>
              <div class="tab_info" id="commentTabBlockNew">
                  <ul class="pagination"><li><a href="https://www.smzdm.com/p/14039324/#comments" class="pageCurrent">1</a></li><li><a href="https://www.smzdm.com/p/14039324/p2/#comments">2</a></li><li class="pagedown"><a href="https://www.smzdm.com/p/14039324/p2/#comments"><i class="icon-angle-right-o-thin"></i></a></li><li class="jumpToPage">转至<input type="text" class="input_num" id="input_num">页</li><li><a href="javascript:void(0);" class="a_jumpTo" onclick="return on_check_comment_page(2, 'https://www.smzdm.com/p/14039324/', this)">GO</a></li></ul>
                  <ul class="comment_listBox">
                      <span id="li_comment_new"></span>
                          <li v-bind:key="item.id" v-for="item in this.blogList" class="comment_list" id="li_comment_132977043" itemprop="review" itemscope="" itemtype="http://schema.org/Review">
<PcComment @readyToReply="showCommentDialog" :data=item ></PcComment>
                          </li>




                          </ul>
                                        </div>

                                                <div id="quickReplyDiv" style="display:none;">
                                            <div class="quickReply" id="quickReply">
                                                <form id="commentquickform" class="commentquickform" onsubmit="return false;" method="post" action="">
                                                    <input type="hidden" id="type" name="type" class="quickCommentType" value="3">
                                                    <input type="hidden" name="comment_type" class="quickCommentType" value="3">
                                                    <input type="hidden" id="pid" name="pid" value="14039324">
                                                    <input type="hidden" id="parentid" name="parentid" class="parentid" value="0">
                                                    <textarea rows="1" cols="70" id="quickComment" name="content" class="textarea_comment textarea_quick" style="height: auto;"></textarea>
                                                    <div id="loadingquick" style="display: none;"><img style="vertical-align:middle;" src="//www.smzdm.com/resources/public/img/loading_comment.gif"> </div>
                                                    <p id="quick_error" class="red"></p>
                                                    <div class="comment_operate">
                                                        <div class="comment_smileBox">
                                                            <i class="icon-small" id="smileImg"><!--[if lt IE 8]>表情<![endif]--></i>
                                                            <div class="smileLayerBg" id="smiley" style="display:none;">
                                                            <ul class="smileBox"><li><a href="javascript:void(0)" class="smile147" default-data="[吃土]" title="吃土"></a></li><li><a href="javascript:void(0)" class="smile148" default-data="[大白菜]" title="大白菜"></a></li><li><a href="javascript:void(0)" class="smile149" default-data="[大妈三宝]" title="大妈三宝"></a></li><li><a href="javascript:void(0)" class="smile150" default-data="[解毒]" title="解毒"></a></li><li><a href="javascript:void(0)" class="smile151" default-data="[领券防身]" title="领券防身"></a></li><li><a href="javascript:void(0)" class="smile152" default-data="[榴莲]" title="榴莲"></a></li><li><a href="javascript:void(0)" class="smile153" default-data="[买买买]" title="买买买"></a></li><li><a href="javascript:void(0)" class="smile154" default-data="[钱包被掏空]" title="钱包被掏空"></a></li><li><a href="javascript:void(0)" class="smile155" default-data="[让你买]" title="让你买"></a></li><li><a href="javascript:void(0)" class="smile156" default-data="[神价格]" title="神价格"></a></li><li><a href="javascript:void(0)" class="smile157" default-data="[手慢无]" title="手慢无"></a></li><li><a href="javascript:void(0)" class="smile158" default-data="[损失几个亿]" title="损失几个亿"></a></li><li><a href="javascript:void(0)" class="smile159" default-data="[铁茄子]" title="铁茄子"></a></li><li><a href="javascript:void(0)" class="smile160" default-data="[吐血推荐]" title="吐血推荐"></a></li><li><a href="javascript:void(0)" class="smile161" default-data="[种草]" title="种草"></a></li><li><a href="javascript:void(0)" class="smile162" default-data="[紫薯布丁]" title="紫薯布丁"></a></li><li><a href="javascript:void(0)" class="smile163" default-data="[doge]" title="doge"></a></li><li><a href="javascript:void(0)" class="smile164" default-data="[ok了]" title="ok了"></a></li><li><a href="javascript:void(0)" class="smile165" default-data="[棒棒哒]" title="棒棒哒"></a></li><li><a href="javascript:void(0)" class="smile166" default-data="[捂嘴]" title="捂嘴"></a></li><li><a href="javascript:void(0)" class="smile167" default-data="[吃]" title="吃"></a></li><li><a href="javascript:void(0)" class="smile168" default-data="[吃药]" title="吃药"></a></li><li><a href="javascript:void(0)" class="smile169" default-data="[大哭]" title="大哭"></a></li><li><a href="javascript:void(0)" class="smile170" default-data="[好冷啊]" title="好冷啊"></a></li><li><a href="javascript:void(0)" class="smile171" default-data="[鬼脸]" title="鬼脸"></a></li><li><a href="javascript:void(0)" class="smile172" default-data="[害羞了]" title="害羞了"></a></li><li><a href="javascript:void(0)" class="smile173" default-data="[嘿嘿]" title="嘿嘿"></a></li><li><a href="javascript:void(0)" class="smile174" default-data="[花痴]" title="花痴"></a></li><li><a href="javascript:void(0)" class="smile175" default-data="[被吓到]" title="被吓到"></a></li><li><a href="javascript:void(0)" class="smile176" default-data="[很惊讶]" title="很惊讶"></a></li><li><a href="javascript:void(0)" class="smile177" default-data="[开花]" title="开花"></a></li><li><a href="javascript:void(0)" class="smile178" default-data="[开心]" title="开心"></a></li><li><a href="javascript:void(0)" class="smile179" default-data="[看不到]" title="看不到"></a></li><li><a href="javascript:void(0)" class="smile180" default-data="[抠鼻孔]" title="抠鼻孔"></a></li><li><a href="javascript:void(0)" class="smile181" default-data="[么么哒]" title="么么哒"></a></li><li><a href="javascript:void(0)" class="smile182" default-data="[难过]" title="难过"></a></li><li><a href="javascript:void(0)" class="smile183" default-data="[生气]" title="生气"></a></li><li><a href="javascript:void(0)" class="smile184" default-data="[唔]" title="唔"></a></li><li><a href="javascript:void(0)" class="smile185" default-data="[笑]" title="笑"></a></li><li><a href="javascript:void(0)" class="smile186" default-data="[耶]" title="耶"></a></li><li><a href="javascript:void(0)" class="smile187" default-data="[疑问]" title="疑问"></a></li><li><a href="javascript:void(0)" class="smile188" default-data="[晕倒]" title="晕倒"></a></li><li><a href="javascript:void(0)" class="smile189" default-data="[再见]" title="再见"></a></li><li><a href="javascript:void(0)" class="smile190" default-data="[赞]" title="赞"></a></li></ul><ul class="smileBox" style="display:none;"><li><a href="javascript:void(0)" class="smile23" default-data="[高兴]" title="高兴"></a></li><li><a href="javascript:void(0)" class="smile24" default-data="[皱眉]" title="皱眉"></a></li><li><a href="javascript:void(0)" class="smile25" default-data="[阴笑]" title="阴笑"></a></li><li><a href="javascript:void(0)" class="smile26" default-data="[鄙视]" title="鄙视"></a></li><li><a href="javascript:void(0)" class="smile27" default-data="[邪恶]" title="邪恶"></a></li><li><a href="javascript:void(0)" class="smile28" default-data="[大囧]" title="大囧"></a></li><li><a href="javascript:void(0)" class="smile29" default-data="[惊喜]" title="惊喜"></a></li><li><a href="javascript:void(0)" class="smile30" default-data="[小眼睛]" title="小眼睛"></a></li><li><a href="javascript:void(0)" class="smile31" default-data="[小怒]" title="小怒"></a></li><li><a href="javascript:void(0)" class="smile32" default-data="[无语]" title="无语"></a></li><li><a href="javascript:void(0)" class="smile33" default-data="[傻笑]" title="傻笑"></a></li><li><a href="javascript:void(0)" class="smile34" default-data="[期待]" title="期待"></a></li><li><a href="javascript:void(0)" class="smile35" default-data="[得逞]" title="得逞"></a></li><li><a href="javascript:void(0)" class="smile36" default-data="[呲牙]" title="呲牙"></a></li><li><a href="javascript:void(0)" class="smile37" default-data="[喜极而泣]" title="喜极而泣"></a></li><li><a href="javascript:void(0)" class="smile38" default-data="[晕死]" title="晕死"></a></li><li><a href="javascript:void(0)" class="smile39" default-data="[脸红]" title="脸红"></a></li><li><a href="javascript:void(0)" class="smile40" default-data="[亲亲]" title="亲亲"></a></li><li><a href="javascript:void(0)" class="smile41" default-data="[狂汗]" title="狂汗"></a></li><li><a href="javascript:void(0)" class="smile42" default-data="[困]" title="困"></a></li><li><a href="javascript:void(0)" class="smile43" default-data="[饥饿]" title="饥饿"></a></li><li><a href="javascript:void(0)" class="smile44" default-data="[得意]" title="得意"></a></li><li><a href="javascript:void(0)" class="smile45" default-data="[抠鼻]" title="抠鼻"></a></li><li><a href="javascript:void(0)" class="smile46" default-data="[抽烟]" title="抽烟"></a></li><li><a href="javascript:void(0)" class="smile47" default-data="[内伤]" title="内伤"></a></li><li><a href="javascript:void(0)" class="smile48" default-data="[口水]" title="口水"></a></li><li><a href="javascript:void(0)" class="smile49" default-data="[吐]" title="吐"></a></li><li><a href="javascript:void(0)" class="smile50" default-data="[流鼻涕]" title="流鼻涕"></a></li><li><a href="javascript:void(0)" class="smile51" default-data="[绷带]" title="绷带"></a></li><li><a href="javascript:void(0)" class="smile52" default-data="[吐舌]" title="吐舌"></a></li><li><a href="javascript:void(0)" class="smile53" default-data="[口罩]" title="口罩"></a></li><li><a href="javascript:void(0)" class="smile54" default-data="[不说话]" title="不说话"></a></li><li><a href="javascript:void(0)" class="smile55" default-data="[不出所料]" title="不出所料"></a></li><li><a href="javascript:void(0)" class="smile56" default-data="[装大款]" title="装大款"></a></li><li><a href="javascript:void(0)" class="smile57" default-data="[尴尬]" title="尴尬"></a></li><li><a href="javascript:void(0)" class="smile58" default-data="[喷水]" title="喷水"></a></li><li><a href="javascript:void(0)" class="smile59" default-data="[喷血]" title="喷血"></a></li><li><a href="javascript:void(0)" class="smile60" default-data="[咽气]" title="咽气"></a></li><li><a href="javascript:void(0)" class="smile61" default-data="[哼小曲]" title="哼小曲"></a></li><li><a href="javascript:void(0)" class="smile62" default-data="[吹泡泡]" title="吹泡泡"></a></li><li><a href="javascript:void(0)" class="smile63" default-data="[愤怒]" title="愤怒"></a></li><li><a href="javascript:void(0)" class="smile64" default-data="[冻住]" title="冻住"></a></li><li><a href="javascript:void(0)" class="smile65" default-data="[赞一个]" title="赞一个"></a></li><li><a href="javascript:void(0)" class="smile66" default-data="[差评]" title="差评"></a></li></ul><ul class="smileBox" style="display:none;"><li><a href="javascript:void(0)" class="smile67" default-data="[中指]" title="中指"></a></li><li><a href="javascript:void(0)" class="smile68" default-data="[哦耶]" title="哦耶"></a></li><li><a href="javascript:void(0)" class="smile69" default-data="[拳头]" title="拳头"></a></li><li><a href="javascript:void(0)" class="smile70" default-data="[手掌]" title="手掌"></a></li><li><a href="javascript:void(0)" class="smile71" default-data="[就是你]" title="就是你"></a></li><li><a href="javascript:void(0)" class="smile72" default-data="[过来]" title="过来"></a></li><li><a href="javascript:void(0)" class="smile73" default-data="[吃雪糕]" title="吃雪糕"></a></li><li><a href="javascript:void(0)" class="smile74" default-data="[吃香蕉]" title="吃香蕉"></a></li><li><a href="javascript:void(0)" class="smile75" default-data="[ok]" title="ok"></a></li><li><a href="javascript:void(0)" class="smile76" default-data="[看不见]" title="看不见"></a></li><li><a href="javascript:void(0)" class="smile77" default-data="[无所谓]" title="无所谓"></a></li><li><a href="javascript:void(0)" class="smile78" default-data="[欢呼]" title="欢呼"></a></li><li><a href="javascript:void(0)" class="smile79" default-data="[偷笑]" title="偷笑"></a></li><li><a href="javascript:void(0)" class="smile80" default-data="[无奈]" title="无奈"></a></li><li><a href="javascript:void(0)" class="smile81" default-data="[害羞]" title="害羞"></a></li><li><a href="javascript:void(0)" class="smile82" default-data="[打脸]" title="打脸"></a></li><li><a href="javascript:void(0)" class="smile83" default-data="[想一想]" title="想一想"></a></li><li><a href="javascript:void(0)" class="smile84" default-data="[嘘]" title="嘘"></a></li><li><a href="javascript:void(0)" class="smile85" default-data="[烧香]" title="烧香"></a></li><li><a href="javascript:void(0)" class="smile86" default-data="[抱拳]" title="抱拳"></a></li><li><a href="javascript:void(0)" class="smile87" default-data="[鼓掌]" title="鼓掌"></a></li><li><a href="javascript:void(0)" class="smile88" default-data="[观察]" title="观察"></a></li><li><a href="javascript:void(0)" class="smile89" default-data="[锁眉]" title="锁眉"></a></li><li><a href="javascript:void(0)" class="smile90" default-data="[黑线]" title="黑线"></a></li><li><a href="javascript:void(0)" class="smile91" default-data="[汗]" title="汗"></a></li><li><a href="javascript:void(0)" class="smile92" default-data="[哭泣]" title="哭泣"></a></li><li><a href="javascript:void(0)" class="smile93" default-data="[阴暗]" title="阴暗"></a></li><li><a href="javascript:void(0)" class="smile94" default-data="[暗地观察]" title="暗地观察"></a></li><li><a href="javascript:void(0)" class="smile95" default-data="[潜水]" title="潜水"></a></li><li><a href="javascript:void(0)" class="smile96" default-data="[蜡烛]" title="蜡烛"></a></li><li><a href="javascript:void(0)" class="smile97" default-data="[投降]" title="投降"></a></li><li><a href="javascript:void(0)" class="smile98" default-data="[吐血倒地]" title="吐血倒地"></a></li><li><a href="javascript:void(0)" class="smile99" default-data="[便便]" title="便便"></a></li><li><a href="javascript:void(0)" class="smile100" default-data="[长草]" title="长草"></a></li><li><a href="javascript:void(0)" class="smile101" default-data="[绿帽子]" title="绿帽子"></a></li><li><a href="javascript:void(0)" class="smile102" default-data="[注意安全]" title="注意安全"></a></li><li><a href="javascript:void(0)" class="smile103" default-data="[被砍]" title="被砍"></a></li><li><a href="javascript:void(0)" class="smile104" default-data="[捶打]" title="捶打"></a></li><li><a href="javascript:void(0)" class="smile105" default-data="[肿包]" title="肿包"></a></li><li><a href="javascript:void(0)" class="smile106" default-data="[坐等]" title="坐等"></a></li><li><a href="javascript:void(0)" class="smile107" default-data="[看热闹]" title="看热闹"></a></li><li><a href="javascript:void(0)" class="smile108" default-data="[深思]" title="深思"></a></li><li><a href="javascript:void(0)" class="smile109" default-data="[被监视]" title="被监视"></a></li><li><a href="javascript:void(0)" class="smile110" default-data="[探头]" title="探头"></a></li></ul><ul class="smileBox" style="display:none;"><li><a href="javascript:void(0)" class="smile111" default-data="[看看]" title="看看"></a></li><li><a href="javascript:void(0)" class="smile112" default-data="[嘿嘿嘿]" title="嘿嘿嘿"></a></li><li><a href="javascript:void(0)" class="smile113" default-data="[睁大眼睛]" title="睁大眼睛"></a></li><li><a href="javascript:void(0)" class="smile114" default-data="[伸舌头]" title="伸舌头"></a></li><li><a href="javascript:void(0)" class="smile115" default-data="[吃骨头]" title="吃骨头"></a></li><li><a href="javascript:void(0)" class="smile116" default-data="[摸摸头]" title="摸摸头"></a></li><li><a href="javascript:void(0)" class="smile117" default-data="[被咬了]" title="被咬了"></a></li><li><a href="javascript:void(0)" class="smile118" default-data="[献花]" title="献花"></a></li><li><a href="javascript:void(0)" class="smile119" default-data="[献黄瓜]" title="献黄瓜"></a></li><li><a href="javascript:void(0)" class="smile120" default-data="[献香蕉]" title="献香蕉"></a></li><li><a href="javascript:void(0)" class="smile121" default-data="[举个栗子]" title="举个栗子"></a></li><li><a href="javascript:void(0)" class="smile122" default-data="[该吃药了]" title="该吃药了"></a></li><li><a href="javascript:void(0)" class="smile123" default-data="[卫生纸]" title="卫生纸"></a></li><li><a href="javascript:void(0)" class="smile124" default-data="[＋1]" title="＋1"></a></li><li><a href="javascript:void(0)" class="smile125" default-data="[烧钱]" title="烧钱"></a></li><li><a href="javascript:void(0)" class="smile126" default-data="[有钱任性]" title="有钱任性"></a></li><li><a href="javascript:void(0)" class="smile127" default-data="[丢骨头]" title="丢骨头"></a></li><li><a href="javascript:void(0)" class="smile128" default-data="[丢手雷]" title="丢手雷"></a></li><li><a href="javascript:void(0)" class="smile129" default-data="[丢肥皂]" title="丢肥皂"></a></li><li><a href="javascript:void(0)" class="smile130" default-data="[飞吻]" title="飞吻"></a></li><li><a href="javascript:void(0)" class="smile131" default-data="[钻被窝]" title="钻被窝"></a></li><li><a href="javascript:void(0)" class="smile132" default-data="[谋杀]" title="谋杀"></a></li><li><a href="javascript:void(0)" class="smile133" default-data="[大宝剑]" title="大宝剑"></a></li><li><a href="javascript:void(0)" class="smile134" default-data="[手枪]" title="手枪"></a></li><li><a href="javascript:void(0)" class="smile135" default-data="[鞭子]" title="鞭子"></a></li><li><a href="javascript:void(0)" class="smile136" default-data="[炸药包]" title="炸药包"></a></li><li><a href="javascript:void(0)" class="smile137" default-data="[自杀]" title="自杀"></a></li><li><a href="javascript:void(0)" class="smile138" default-data="[中枪]" title="中枪"></a></li><li><a href="javascript:void(0)" class="smile139" default-data="[击掌]" title="击掌"></a></li><li><a href="javascript:void(0)" class="smile140" default-data="[扇耳光]" title="扇耳光"></a></li><li><a href="javascript:void(0)" class="smile141" default-data="[砸钱]" title="砸钱"></a></li><li><a href="javascript:void(0)" class="smile142" default-data="[抽打]" title="抽打"></a></li><li><a href="javascript:void(0)" class="smile143" default-data="[相爱相杀]" title="相爱相杀"></a></li><li><a href="javascript:void(0)" class="smile144" default-data="[斩杀]" title="斩杀"></a></li><li><a href="javascript:void(0)" class="smile145" default-data="[背刺]" title="背刺"></a></li><li><a href="javascript:void(0)" class="smile146" default-data="[中刀]" title="中刀"></a></li></ul><ul class="smileBox" style="display:none;"><li><a href="javascript:void(0)" class="smile1" default-data="[糗大了]" title="糗大了"></a></li><li><a href="javascript:void(0)" class="smile2" default-data="[示爱]" title="示爱"></a></li><li><a href="javascript:void(0)" class="smile3" default-data="[晕]" title="晕"></a></li><li><a href="javascript:void(0)" class="smile4" default-data="[酷]" title="酷"></a></li><li><a href="javascript:void(0)" class="smile5" default-data="[流泪]" title="流泪"></a></li><li><a href="javascript:void(0)" class="smile6" default-data="[饿了]" title="饿了"></a></li><li><a href="javascript:void(0)" class="smile7" default-data="[闭嘴]" title="闭嘴"></a></li><li><a href="javascript:void(0)" class="smile8" default-data="[做鬼脸]" title="做鬼脸"></a></li><li><a href="javascript:void(0)" class="smile9" default-data="[馋]" title="馋"></a></li><li><a href="javascript:void(0)" class="smile10" default-data="[坏笑]" title="坏笑"></a></li><li><a href="javascript:void(0)" class="smile11" default-data="[抓狂]" title="抓狂"></a></li><li><a href="javascript:void(0)" class="smile12" default-data="[呵呵]" title="呵呵"></a></li><li><a href="javascript:void(0)" class="smile13" default-data="[淡定]" title="淡定"></a></li><li><a href="javascript:void(0)" class="smile14" default-data="[冷汗]" title="冷汗"></a></li><li><a href="javascript:void(0)" class="smile15" default-data="[色]" title="色"></a></li><li><a href="javascript:void(0)" class="smile16" default-data="[惊讶]" title="惊讶"></a></li><li><a href="javascript:void(0)" class="smile17" default-data="[希望]" title="希望"></a></li><li><a href="javascript:void(0)" class="smile18" default-data="[伤心]" title="伤心"></a></li><li><a href="javascript:void(0)" class="smile19" default-data="[微笑]" title="微笑"></a></li><li><a href="javascript:void(0)" class="smile20" default-data="[惊吓]" title="惊吓"></a></li><li><a href="javascript:void(0)" class="smile21" default-data="[哈哈]" title="哈哈"></a></li><li><a href="javascript:void(0)" class="smile22" default-data="[吃饭]" title="吃饭"></a></li></ul><div class="smilePage"><a href="javascript:void(0)" class="current">1</a><a href="javascript:void(0)">2</a><a href="javascript:void(0)">3</a><a href="javascript:void(0)">4</a><a href="javascript:void(0)">5</a></div></div>
                                                        </div>
                                                        <div class="comment_share">
                                                            <button type="submit" class="btn_sub" id="textCommentSubmitQuick">提交</button>
                                                                                    </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>

                                    </div>
                                            <!-- comment_wrap end -->
                                </section>
</template>
<script type="text/javascript">
import zwIcon from '../icon/zwIcon.vue';
import zwBase from '../../component/zwBase.vue';
import zwButton from '../../component/button/zwButton.vue';
import PcComment from '../../component/datadisplay/PcComment.vue';
export default {
         components:{zwIcon,Comment,zwButton,PcComment},
        props:["data","pid"],// blogviewlist 列表 blogview 每个item  bloginput输入框 引入 zwRichInput
        data () {
            return {
                //data:{},
                  blogList:[],
                  loginUser:{},
                  isLogin:false,
                  curPage:1
            };
        },
        computed: {
            getComputedCls:function(){
                if(this.isLogin){
                    return "btn_sub"
                }else{
                    return "btn_sub_gray"
                }
            }
        },
        mounted () {
        var storeUser = localStorage.getItem('user');
        if(storeUser){
        this.loginUser=eval("("+storeUser+")");
        this.loginUser.face=PATH+"/"+this.loginUser.face;
            this.isLogin=true;
        }
            this.getNews();
        },
        watch: {
            pid(newName, oldName) {
            this.blogList=[];
              this.getNews();
            }
          },
        methods: {

        goLogin:function(){
            goLoginPage();

        },
        showCommentDialog:function(parentCommentId,childCommentId,childFlag){
        console.log("PcComments 收到 showCommentDialog"+parentCommentId+","+childCommentId+","+childFlag);
            //document.getElementsByClassName("post-comment")[0].style.display="block";
            //document.getElementById("pid").value=this.pid;
             //document.getElementById("commentType").value="msginfo";
            for(var i=0;i<this.blogList.length;i++){
                if(!childFlag){
                    if(this.blogList[i].id==parentCommentId){
                                        this.blogList[i].showCommentInput=true;
                                        console.log("找到了");
                                    }else{
                                         this.blogList[i].showCommentInput=false;
                                    }
                                }
                }


        },

        submitComment:function(){
            var pinglun =this.$refs.pinglun.value;
            //var content = document.getElementById("pinglun").value;
            //var pid = document.getElementById("pid").value;
            //var type = document.getElementById("commentType").value;
           // if(type=="msginfo"){
                Ajax.post(PATH+"/goods/comment",{pid:this.pid,content:pinglun,type:1,objId:this.pid},function(result){
                                //刷新页面
                                //this.refresh();
                                //清空值
                                this.$refs.pinglun.value="";
                                this.blogList =[result.data].concat(this.blogList);
                                  document.getElementsByClassName("post-comment")[0].style.display="none";
                            }.Apply(this))
            //}else{
            //Ajax.post(PATH+"/msginfo/add.json",{pid:pid,content:pinglun,type:1},function(result){
                //刷新页面
                //this.refresh();
                //清空值
             //   this.$refs.pinglun.value="";
              //  document.getElementsByClassName("post-comment")[0].style.display="none";
           // }.Apply(this))
            //}
        },
        goNext:function(){
            this.curPage++;this.getNews();
        },
        refresh:function(){
           this.getNews();
        },
        getNews:function( content){
            var jso = {pid:this.pid,curPage:this.curPage,pageSize:10};
            if(!this.pid){
            return ;
            //    jso.pid=0;
            }
            Ajax.get(PATH+"/msginfo/list.json",jso,this.showData);
        },
        showData:function(result){
            for(var i=0;i<result.data.length;i++){
            result.data[i].showCommentInput=false;
            this.blogList.push(result.data[i]);
            }
            //this.blogList=result.data;

        },
        hideMask:function(event){//点击黑色部分就关闭评论窗口
            console.log(event.currentTarget)
            console.log("hideMask");
            document.getElementById("reply-mask").style.display="none";
        }
        },


         events: {

          }
    };


</script>
<style>




/**css评论css**/

#comments {
font-size: 14px;
    color: #333;
    margin-top: -30px;
}
#comments {
    padding: 50px 0 20px;
    word-break: break-all;
    word-wrap: break-word;
}


.panelTitle {
    font-size: 18px;
    clear: both;
    overflow: hidden;
    height: 40px;
    line-height: 40px;
    font-weight: 400;
}
.greyNum {
    font-size: 12px;
    color: #999;
    font-weight: 400;
}


.comment_sendwrap {
    font-size: 12px;
    margin-bottom: 16px;
    position: relative;
    padding-top: 20px;
}

#comment_error {
    margin-bottom: 10px;
}
#comment_error, .comment_sendPart {
    padding-left: 80px;
}

.comment_avatar {
    width: 60px;
    float: left;
}

.comment_list .userPic, .comment_sendwrap .userPic {
    float: none;
    display: block;
    margin-right: 0;
}

.userPic {
    width: 60px;
    height: 60px;
    float: left;
    margin-bottom: 8px;
    margin-right: 20px;
    text-align: center;
    overflow: hidden;
}

.userPic img {
    -webkit-border-radius: 100%;
    border-radius: 100%;
}

img {
    font-size: 0;
    border-style: none;
    vertical-align: middle;
}




.noLogin .comment_sendPart {
    position: relative;
}


.noLogin .comment_tips {
    display: block;
}

.comment_tips {
    position: absolute;
    left: 90px;
    top: 10px;
    font-size: 12px;
    color: #999;
    display: none;
}

.textarea_comment {
    padding: 10px;
    height: 60px;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    width: 582px;
    margin-bottom: 10px;
    font-size: 12px;
    line-height: 20px;
    color: #999;
    resize: none;
    background-color: #f5f5f5;
    border: none;
}
.comment_operate {
    height: 28px;
    line-height: 28px;
}

.comment_operate {
    height: 28px;
    line-height: 28px;
}

.comment_operate .comment_smileBox {
    width: 380px;
    height: 40px;
    float: left;
    position: relative;
    z-index: 1;
}
i.icon-small {
    padding-left: 1px;
    font-size: 22px;
    color: #999;
}

.comment_operate .comment_share {
    width: 90px;
    height: 28px;
    float: right;
    color: #999;
}

.comment_operate .btn_sub, .comment_operate .btn_subGrey {
    width: 90px;
    height: 28px;
    text-align: center;
    background-color: #f04848;
    color: #fff;
    -webkit-border-radius: 2px;
    border-radius: 2px;
    font-size: 14px;
    float: right;
    cursor: pointer;
}

.btn_grey, .comment_operate .btn_subGrey {
    background-color: #ccc;
}


#comments .icon-medal {
    display: inline-block;
    height: 16px;
    font-size: 0;
    vertical-align: -4px;
}
.comment_list .user-box-new .user-medal-new {
    width: 12px;
    height: 12px;
    position: absolute;
    right: -1px;
    bottom: 0;
}
.comment_list .user-box-new .user-medal-new img {
    width: 100%;
    height: 100%;
}
#comments .icon-medal img {
    height: 16px;
    display: block;
}
#comments .icon-medal a {
    display: inline-block;
    margin-right: 4px;
    height: 16px;
}
/**css表情css**/


.smileLayerBg {
    position: absolute;
    top: 34px;
    left: 0;
    clear: both;
    width: 386px;
    height: 176px;
    padding: 12px;
    background-color: #f5f5f5;
    border: 1px solid #dcdcdc;
}
ul.smileBox {
    width: 386px;
    height: 140px;
    clear: both;
}
ul.smileBox li {
    width: 36px;
    height: 36px;
    float: left;
    margin: -1px 0 0 -1px;
    padding: 0;
    border-bottom: 0;
}

ul.smileBox li a.smile147 {
    background-position: 0 -71px;
}
ul.smileBox li a {
    background: url(//www.smzdm.com/resources/public/img/static_v2.gif) no-repeat;
    width: 34px;
    height: 34px;
    display: block;
    border: 1px solid #dcdcdc;
    background-color: #fff;
}
.smilePage {
    text-align: right;
    width: 384px;
    line-height: 24px;
    padding-top: 12px;
}

.smilePage a.current, .smilePage a:hover {
    background-color: #f04848;
    color: #fff!important;
    font-weight: 700;
    text-decoration: none;
}

.smilePage a {
    display: inline-block;
    width: 24px;
    height: 24px;
    margin-left: 4px;
    text-align: center;
    -webkit-border-radius: 2px;
    border-radius: 2px;
}



/**css评论列表区**/

.comment_listBox {
    margin-bottom: 0;
}
/**css评论每一行**/
.comment_list {
    padding-bottom: 20px;
    border-bottom: 1px solid #f5f5f5;
    margin-bottom: 16px;
}

/**css评论头像**/
.comment_avatar {
    width: 60px;
    float: left;
}
.comment_list .comment_avatar {
    height: auto;
    font-size: 12px;
    text-align: center;
    width: 36px;
    padding-right: 16px;
    position: relative;
    color: #999;
}
.comment_list .user-box-new {
    width: 36px;
    height: 36px;
    margin-bottom: 10px;
    margin-right: 0;
    text-align: center;
    float: none;
    position: relative;
}
.comment_list .user-box-new .user-avatar {
    width: 100%;
    height: 100%;
    -webkit-border-radius: 50%;
    border-radius: 50%;
    overflow: hidden;
    display: block;
}

.comment_list .user-box-new .user-avatar img {
    width: 100%;
    height: 100%;
}
img {
    font-size: 0;
    border-style: none;
    vertical-align: middle;
}

.comment_list .comment_avatar .grey {
    white-space: nowrap;
}

.comment_conBox {
    width: 630px;
    line-height: 24px;
    color: #666;
    float: left;
    font-size: 14px;
}

.comment_conBox {
    width: 630px;
    line-height: 24px;
    color: #666;
    float: left;
    font-size: 14px;
}


/**css评论右上角时间**/
.comment_avatar_time {
    height: 36px;
    line-height: 36px;
    overflow: hidden;
    margin-bottom: 4px;
}

.comment_avatar_time .time {
    float: right;
    margin-top: 2px;
    font-size: 12px;
    color: #999;
}
/**css用户名称**/
.comment_avatar_time>a.a_underline {
    margin-right: 4px;
    color: #333;
}
.comment_avatar_time>a.a_underline {
    margin-right: 4px;
    color: #333;
}

/**css月亮图片**/
.rank {
    display: inline;
    font-size: 12px;
}

i.icon-biaoqing-moon, i.icon-biaoqing-star, i.icon-biaoqing-sun, i.icon-crown {
    color: #fdcc38;
    margin-left: 4px;
    vertical-align: -2px;
    font-size: 16px;
}

.icon-biaoqing-moon:before {
    content: "\e97f";
}



/**css评论内容**/
.comment_con p {
    word-wrap: break-word;
    word-break: break-all;

}
/**css评论下方 备注**/
.comment_action {
    text-align: right;
}
/**css评论下方 备注  那个终端发送的**/
.comment_action .come_from {
    float: left;
    padding-right: 12px;
    color: #999;
    font-size: 12px;
}
/**css自家的app软件连接**/
.comment_action .come_from a {
    padding-left: 0;
    color: #5188a6;
}

/**css举报**/
.comment_action a {
    padding-left: 16px;
    color: #999;
    font-size: 12px;
}

.clear {
    float: none!important;
    clear: both;
    height: 0;
    overflow: hidden;

}


/**css 他人评论引用**/
.comment_blockquote:last-child, .comment_conBox blockquote:last-child {
    border-bottom: none;
}
.comment_blockquote, .comment_conBox blockquote {
    background-color: #f5f5f5;
    padding: 15px 19px;
    border-bottom: 1px solid #eee;
}


.comment_floor {
    float: right;
    color: #999;
    margin: -14px -12px 0 0;
}

blockquote .comment_con {
    padding-right: 15px;
    padding-top: 0;
    overflow: hidden;
}

blockquote .comment_con a.user_name {
    color: #333;
}

blockquote .comment_con p {
    display: inline;
}

/*
.comment_action{
    visibility:hidden	;
}

.comment_action:hover{
    visibility:visible	;
}*/
</style>
