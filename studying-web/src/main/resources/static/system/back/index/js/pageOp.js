layui.define(function (exports) {
    var $ = layui.jquery,APP_BODY='#LAY_app_body',TABS_BODY = 'layadmin-tabsbody-item',SHOW = 'layui-show',
        Tabs_HEADER = "LAY_app_tabsheader",element = layui.element,FILTER_TAB_TBAS= 'layadmin-layout-tabs',
        admin={
        //pageTabs是否开启多页
        pageTabs: true,
        // 记录最近一次点击的页面标签数据
        tabsPage:{},
        // 获取页面标签主体元素
        tabsBody:function (index) {
            return $(APP_BODY).find('.'+TABS_BODY).eq(index||0);
        }
        ,tabsBodyChange:function (index,options) {
            options = options || {};
            admin.tabsBody(index).addClass(SHOW).siblings().removeClass(SHOW);
            events.rollPage('auto',index);
        },closeThisTabs:function () {
            if (admin.tabsPage.index) return;
            $(Tabs_HEADER).eq(admin.tabsPage.index).find('.layui-tab-close').trigger('click');
        }
    };
    //事件
    var events = admin.events = {
        rollPage:function(type,index){
            var tabsHeader = $('#'+Tabs_HEADER),liItem = tabsHeader.children('li'),
                //获得匹配的元素集中第一个元素的属性只或是设置每一个匹配元素的一个或多个属性
                scrollWidth = tabsHeader.prop('scrollWidth'),tabsLeft = parseFloat(tabsHeader.css('left')),
                //获取元素集合中第一个元素但钱计算宽度值，包括padding，border和选择性的margin
            outerWidth = tabsHeader.outerWidth();
            if(type=='left'){
                if (!tabsHeader&&tabsLeft<=0) return;
                var preLeft = -tabsLeft - outerWidth;
                liItem.each(function (index,item) {
                    var li = $(item),left = li.position().left;
                    if(left>=preLeft){
                        tabsHeader.css('left',-left);
                        return false;
                    }
                })
            }else if (type === 'auto'){
                (function () {
                    var thisLi = liItem.eq(index),thisLeft;
                    if (!thisLi[0]) return;
                    thisLeft = thisLi.position().left;
                    //当目标标签在可视区左侧
                    if (thisLeft<-tabsLeft){
                        return tabsHeader.css('left',-thisLeft);
                    }
                    //当目标标签在可视区右侧时
                    if (thisLeft+thisLi.outerWidth()>=outerWidth-tabsLeft){
                        var subLeft = thisLeft + thisLi.outerWidth() - (outerWidth - tabsLeft);
                        liItem.each(function (i,item) {
                            var li = $(item),left = li.position().left;
                            if (left+tabsLeft>0){
                                if (left+tabsLeft>subLeft){
                                    tabsHeader.css('left',-left);
                                    return false;
                                }
                            }
                        })
                    }
                })();
            }else{
                liItem.each(function (i,item) {
                    var li = $(item),left = li.position().left;
                    if (left+li.outerWidth()>=outerWidth-tabsLeft){
                        tabsHeader.css('left',-left);
                        return;
                    }
                });
            }
        },
        leftPage:function () {
            events.rollPage('left');
        },
        rightPage:function () {
            events.rollPage();
        }
    };
    //输出
    exports('pageOp',admin);
});