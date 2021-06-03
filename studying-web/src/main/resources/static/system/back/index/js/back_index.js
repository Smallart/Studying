layui.extend({
    pageOp : "/system/back/index/js/pageOp"
}).use(['jquery','element','pageOp'],function () {
    var pageOp = layui.pageOp,
    tabsPage = pageOp.tabsPage,
    $ = layui.jquery,
    element = layui.element,
    $body=$('body'),
    events={
        //关闭当前页面
        closeThisTab:function () {
            pageOp.closeThisTab();
        },
        //关闭其它页面
        closeOtherTabs:function () {
            $('#LAY_app_tabsheader>li:not(.layui-this):gt(0)').children('i').click();
        },
        //关闭全部页面
        closeAllTabs:function () {
            $('#LAY_app_tabsheader>li:gt(0)').children('i').click();
        },
        //全屏
        fullscreen:function () {
            var $this = $(this),SCREEN_FULL='layui-icon-screen-full',SCREEN_REST='layui-icon-screen-restore',
            iconElem = $this.find('i');
            if (iconElem.hasClass(SCREEN_FULL)){
                var elem = document.body;
                if (elem.webkitRequestFullScreen){
                    elem.webkitRequestFullScreen();
                }else if(elem.mozRequestFullScreen){
                    elem.mozRequestFullScreen();
                }else if (elem.requestFullscreen){
                    elem.requestFullscreen();
                }
                iconElem.addClass(SCREEN_REST).removeClass(SCREEN_FULL);
            }else{
                var elem = document;
                if(elem.webkitCancelFullScreen){
                    elem.webkitCancelFullScreen();
                } else if(elem.mozCancelFullScreen) {
                    elem.mozCancelFullScreen();
                } else if(elem.cancelFullScreen) {
                    elem.cancelFullScreen();
                } else if(elem.exitFullscreen) {
                    elem.exitFullscreen();
                }
                iconElem.addClass(SCREEN_FULL).removeClass(SCREEN_REST);
            }
        },
        //刷新
        refresh:function () {
            var ELEM_IFRAME = '.layadmin-iframe',length=$('.layadmin-tabsbody-item').length;
            if (tabsPage.index>=length){
                pageOp.tabsPage.index = length - 1;
            }
            var iframe = pageOp.tabsBody(pageOp.tabsPage.index).find(ELEM_IFRAME);
            iframe[0].contentWindow.location.reload(true);
        },
        //伸缩
        flexible:function () {
            var ICON_SHRINK = 'layui-icon-shrink-right',ICON_SPREAD = 'layui-icon-spread-left',CLASS_SHRINK='layuiadmin-side-shrink';
            var iconElem = $('#LAY_app_flexible'),isSpread = iconElem.hasClass(ICON_SPREAD);
            if (isSpread){
                $('#LAY_app_flexible').removeClass(ICON_SPREAD).addClass(ICON_SHRINK);
                $('#LAY_app').removeClass(CLASS_SHRINK);
                offMenuMouseEven();
            }else{
                $('#LAY_app_flexible').removeClass(ICON_SHRINK).addClass(ICON_SPREAD);
                $('#LAY_app').addClass(CLASS_SHRINK);
                menuMouseEven();
            }
        }
    };

    init();
    //初始化菜单
    function init() {
        menus();
    }

    // function openTabsPage(url,text) {
    //     var marchTo,tabs = $('#LAY_app_tabsheader>li'),path = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, '');
    //     tabs.each(function (index) {
    //        var li = $(this),layId = li.attr('lay-id');
    //        if (layId === url){
    //            marchTo = true;
    //            tabsPage.index = index;
    //        }
    //     });
    //     text = text || '新标签页';
    //     if (pageOp.pageTabs){
    //         //如果没有在选项卡中匹配到，则追加选项卡
    //         if (!marchTo){
    //             $('#LAY_app_body').append([
    //                 '<div class="layadmin-tabsbody-item">'
    //                 ,'<iframe src="'+ url +'" frameborder="0" class="layadmin-iframe"></iframe>'
    //                 ,'</div>'
    //             ].join(''));
    //             tabsPage.index = tabs.length;
    //             element.tabAdd('layadmin-layout-tabs', {
    //                 title: '<span>'+ text +'</span>'
    //                 ,id: url
    //                 ,attr: path
    //             });
    //         }
    //     }else {
    //       var iframe =  pageOp.tabsBody(tabsPage.index).find('.layadmin-iframe');
    //       iframe[0].contentWindow.location.href = url;
    //     }
    //     element.tabChange('layadmin-layout-tabs',url);
    //     pageOp.tabsBodyChange(tabsPage.index,{
    //         url:url,
    //         text:text
    //     })
    // }
    //监听tab标签页，联动左边菜单栏
    element.on('tab(layadmin-layout-tabs)',function (data) {
        var url = $(this).attr('lay-id'),sideMenu=$('#LAY-system-side-menu'),SIDE_NAV_ITEMD='layui-nav-itemed',
            matchMenu=function (items) {
                items.each(function (index,item) {
                    var $item = $(item),data = getData($item),listChildren = data.list.children('dd'),
                    marched = url === data.a.attr('lay-href');
                    if (marched){
                        var selected = data.list[0] ? SIDE_NAV_ITEMD:'layui-this';
                        $item.addClass(selected).siblings().removeClass(selected);
                        $item.parents("dd").addClass('layui-nav-itemed');
                        return false;
                    }
                    matchMenu(listChildren);
                });
            },
            getData = function (item) {
                return {
                    list: item.children('.layui-nav-child'),
                    a: item.children('*[lay-href]')
                }
            };
        sideMenu.find('.layui-this').removeClass('layui-this');
        matchMenu(sideMenu.children('li'));
        pageOp.tabsBodyChange(data.index,{
            url:url,
            text:$(this).children('span').text()
        });
    });
    //监听菜单栏，联动tab标签页
    element.on('nav(menuFilter)',function (data) {
        if (data.attr('lay-href')!=null|undefined){
            pageOp.openTabsPage(data.attr('lay-href'),data.text());
        }
    });
    //选项卡删除时触发
    element.on('tabDelete(layadmin-layout-tabs)',function (data) {
        data.index && pageOp.tabsBody(data.index).remove();
        pageOp.tabsBodyChange(data.index,{});
    });
    //点击事件
    $body.on('click','*[studying-event]',function () {
        var $this = $(this),attrEvent = $this.attr('studying-event');
        events[attrEvent]&&events[attrEvent].call(this,$this);
    });
    // 侧边栏添加监听事件
    function menuMouseEven(){
        $('#LAY-system-side-menu').on('mouseenter','li',function () {
            layer.open({
                type:4,
                content: [$(this).children('a').attr('lay-tips'),this],
                shade:0,
                closeBtn: 0
            });
        });
        $('#LAY-system-side-menu').on('mouseleave','li',function () {
            layer.close(layer.index);
        });
    }
    // 消除侧边栏监听事件
    function offMenuMouseEven() {
        $('#LAY-system-side-menu').unbind();
    }

    // 初始化菜单
    function menus() {
        fetch('/system/menus').then(response=>response.json()).then(result=>{
            if(result.code==0){
                const sideMenu = $('#LAY-system-side-menu');
                let htmlOuterItem='';
                for (let dataKey of result.data) {
                    let htmlInnerItem = createItemsHtml(dataKey.childrenItems,0);
                    htmlOuterItem +=
                    `<li data-name="${dataKey.menuName}" class="layui-nav-item">
                        <a href="javascript:;" lay-tips="${dataKey.menuName}" lay-direction="${dataKey.menuId}">
                            <i class="${dataKey.icon}"></i>
                            <cite>${dataKey.menuName}</cite>
                        </a>
                        ${htmlInnerItem}
                    </li>`;
                }
                sideMenu.html(htmlOuterItem);
                sideMenu.find('li:first-child').addClass('layui-nav-itemed');
                element.render();
            }
        }).catch(events=>console.error(events));
    }

    // 创建ItemsHTML
    function createItemsHtml(items,level) {
        let innerHtml='';
        for (let itemsKey of items) {
            let childInnerHtml='';
            if (itemsKey.childrenItems!=null|undefined){
                childInnerHtml=createItemsHtml(itemsKey.childrenItems,level+1);
            }
            let href = itemsKey.url=='#'? 'href="javascript:;"' : `lay-href="${itemsKey.url}"`;
            innerHtml+=
                `<dd data-name="${itemsKey.menuName}">
                    <a ${href} style="padding-left: ${45+15*level}px">${itemsKey.menuName}</a>
                    ${childInnerHtml}
                </dd>`;
        }
        let outHtml=`<dl class="layui-nav-child">
                        ${innerHtml}                        
                    </dl>`;
        return outHtml;
    }

});