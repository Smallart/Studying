layui.config({
    base: '/expandjs/'
}).extend({
    JqueryZtree: 'ztree/jquery-ztree'
}).use(['jquery','layer','JqueryZtree','form'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        JqueryZtree = layui.JqueryZtree,
        form = layui.form,
        setting = {
            data:{
                simpleData: {
                    enable: true,
                    idKey: "menuId",
                    pIdKey: "parentId",
                    rootPId: 0
                },
                key:{
                    name:"menuName"
                }
            }
        },zTreeObj;

    init();
    function init() {
        initTree();
    }
    function initTree(){
        $(document).ready(function () {
            $.get('/system/menu/menusZtree',function (data) {
                zTreeObj = $.fn.zTree.init($('#menuTree'),setting,data);
            });
        });
    }

});