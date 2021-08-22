layui.config({
    base: '/expandjs/'
}).extend({
    iconHhysFa: 'iconHhys/iconHhysFa'
}).use(['jquery','form','iconHhysFa'],function () {
    var $ = layui.jquery,
        form = layui.form,
        iconHhysFa = layui.iconHhysFa,
        menuId = $('input[name="menuId"]').val();

    const url = $('#url'),openStyle = $('#openStyle'),
        perms= $('#perms'),icon=$('#icon'),menuStatus=$('#menuStatus'),
        index = parent.layer.getFrameIndex(window.name),
        parentMenu = $('input[name="parentMenu"]'),
        parentIdInput = $('input[name="parentId"]');

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
                    url: '/system/menu/checkMenuInSameParent?menuId='+menuId+'&menuName='+value+'&parentId='+parentId,
                    success:function (res) {
                        exist = res;
                    }
                });
            }
            if (exist){
                return '该菜单名称存在';
            }
        },
        menuType:function (value) {
            if (value==''|undefined){
                return '请选择该菜单类型'
            }
        }
    });

    init();
    function init() {
        initForm();
    }
    
    function initForm() {
        $.get('/system/menu/init?menuId='+menuId,function (res) {
            let data = res.data;
            form.val('menuForm',{
                menuId: data.menuId,
                parentId: data.parentId,
                menuType: data.menuType,
                menuName: data.menuName,
                url: data.url,
                perms: data.perms,
                order: data.orderNum,
                menuStatus: data.visible
            });
            parentMenu.val(data.parentName?data.parentName:'主目录');
            iconHhysFa.checkIcon('iconHhysFa',data.menuIcon);
            switch(data.menuType){
                case 'M':
                    radioM();
                    break;
                case 'C':
                    radioC();
                    break;
                case 'F':
                    radioF();
                    break;
            }
            parent.layui.layer.iframeAuto(index);
            if(data.icon!=undefined|''){
                iconHhysFa.checkIcon('iconHhysFa',data.icon);
            }
        });
    }

    function radioM(){
        url.hide();
        openStyle.hide();
        perms.hide();
        icon.show();
        menuStatus.show();
    }
    function radioC(){
        url.show();
        openStyle.show();
        perms.show();
        icon.show();
        menuStatus.show();
    }
    function radioF(){
        url.hide();
        openStyle.hide();
        icon.hide();
        menuStatus.hide();
        perms.show();
    }

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
        cellWidth:'80px',
        // 点击回调
        click: function(data) {
            console.log(data);
        },
        // 渲染成功后的回调
        success: function(d) {
            console.log(d);
        }
    });

    // 提交
    form.on('submit(*)',function (obj) {
        let data = obj.field;
        let params = {
            menuId: menuId,
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
            url: '/system/menu/edit',
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
});