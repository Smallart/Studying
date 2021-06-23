layui.config({
    base: '/expandjs/'
}).extend({
    iconHhysFa: 'iconHhys/iconHhysFa'
}).use(['jquery','iconHhysFa','form','layer'],function () {
    let $ =layui.jquery,iconHhysFa = layui.iconHhysFa,
        form = layui.form,
        layer = layui.layer,
        menuType = 0,currentIndex = 0,menuId = $('input[name="menuId"]').val();

    const url = $('#url'),openStyle = $('#openStyle'),
        perms= $('#perms'),icon=$('#icon'),menuStatus=$('#menuStatus'),
        index = parent.layer.getFrameIndex(window.name),
        parentMenu = $('input[name="parentMenu"]'),
        parentIdInput  = $('input[name="parentId"]'),
        count = 3;

    // 表单自定义验证
    form.verify({
        menuName:function (value) {
            if (value==null|undefined){
                return '该值不能为空';
            }
            let parentId = parentIdInput.val();
            let exist = false;
            checkNameIsExist();
            function checkNameIsExist(){
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/menu/checkMenuInSameParent?menuName='+value+'&parentId='+parentId,
                    success:function (res) {
                        exist = res;
                    }
                });
            }
            if (exist){
                return '该菜单名称存在';
            }
        },
        menuType:function (value,item) {
            currentIndex++;
            if ($(item).is(':checked')){
                menuType = 1;
            }
            if (currentIndex==count){
                currentIndex = 0;
                if (menuType==0){
                    menuType = 0;
                    return '菜单类型不能为空';
                }
            }
        },
        order:function (value) {
            if (isNaN(value)){
                return '显示排序输入值要是数值'
            }
        }
    });

    init();
    function init(){
        initTree();
    }

    function initTree(){
        if (!menuId){
            parentMenu.val('主目录');
            parentIdInput.val(0);
        }else{
            $.get('/system/menu/init?menuId='+menuId,function (resp) {
                parentMenu.val(resp.data.menuName);
                parentIdInput.val(resp.data.menuId);
            });
        }

    }

    iconHhysFa.render({
        // 选择器，推荐使用input
        elem: '#iconHhysFa',
        // 数据类型：fontClass/awesome，推荐使用fontClass
        type: 'fontClass',
        // 是否开启搜索：true/false，默认true
        search: true,
        // 是否开启分页：true/false，默认true
        page: true,
        // 每页显示数量，默认12
        limit: 12,
        cellWidth:'80px'
    });

    form.on("radio(menuType)",function (obj) {
        switch(obj.value){
            case 'M':
                url.hide();
                openStyle.hide();
                perms.hide();
                icon.show();
                menuStatus.show();
                break;
            case 'C':
                url.show();
                openStyle.show();
                perms.show();
                icon.show();
                menuStatus.show();
                break;
            case 'F':
                url.hide();
                openStyle.hide();
                icon.hide();
                menuStatus.hide();
                perms.show();
                break;
        }
        parent.layui.layer.iframeAuto(index);
    });

    //表单提交事件
    form.on("submit(*)",function (obj) {
        let data = obj.field;
        let params = {
            parentId: data.parentId,
            menuType: data.menuType,
            menuName: data.menuName,
            url: data.url,
            perms: data.perms,
            orderNum: data.order,
            menuIcon: getSelectIcon(),
            visible: (data?.menuStatus)?data.menuStatus:0
        };
        $.ajax({
            url: '/system/menu/save',
            type: 'post',
            data: JSON.stringify(createParams(params)),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
            }
        });
        return false;
    });

    function createParams(params) {
        switch (params.menuType) {
            case 'M':
                delete params.url;
                delete params.perms;
                break;
            case 'C':
                break;
            case 'F':
                delete params.url;
                delete params.menuIcon;
                delete params.visible;
                break;
        }
        return params;
    }

    function getSelectIcon(){
        let clazz = $('.layui-iconpicker-icon>i').attr('class');
        return clazz;
    }


    /*
        上级菜单事件
     */
    parentMenu.on('click',function (data) {
        layer.open({
            title: '选择上级菜单',
            type: 2,
            content: '/system/menu/selectMenu',
            area:['400px','400px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                // 获得子弹出框的树
                let zTreeObj = layero.find('iframe')[0].contentWindow.layui.jquery.fn.zTree.getZTreeObj('menuTree');
                let selectMenu = zTreeObj.getSelectedNodes()[0];
                console.log(selectMenu);
                $('input[name="parentId"]').val(selectMenu.menuId);
                form.val('menuForm',{
                    "parentMenu": selectMenu.oldname??selectMenu.menuName
                });
                layer.close(index);
            },
        });
    });

});