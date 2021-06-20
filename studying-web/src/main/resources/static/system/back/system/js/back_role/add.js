layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','layer','studying'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        studying = layui.studying,
        studyingConfig = {
            form:{
                enable:true,
                events:{
                    submit:{
                        filter: '*',
                        event: formSubmitEvent
                    },
                    checkbox:{
                        filter: 'treeOp',
                        event: formCheckboxEvent
                    }
                },
                dateTime:{
                    enable: true
                },
                verify:{
                    enable: true,
                    rule:{
                        roleName:function (value,item) {
                            let exist = false;
                            checkNameIsExist();
                            function checkNameIsExist(){
                                $.ajax({
                                    async: false,
                                    method: 'get',
                                    url: '/system/role/checkRoleNameUnique?roleName='+value,
                                    success:function (res) {
                                        exist = res.data;
                                    }
                                });
                            }
                            if (exist){
                                return "该登录账号已被注册";
                            }
                        },
                        roleKey:function (value,item) {
                            let exist = false;
                            checkKeyIsExist();
                            function checkKeyIsExist(){
                                $.ajax({
                                    async: false,
                                    method: 'get',
                                    url: '/system/role/checkRoleKeyUnique/'+value,
                                    success:function (res) {
                                        exist = res.data;
                                    }
                                });
                            }
                            if (exist){
                                return "该字符已被注册";
                            }
                        },
                        sort:function (value,item) {
                            if (isNaN(value)){
                                return "填入的内容要为数字";
                            }
                        }
                    }
                }
            },
            ztree:{
                enable:true,
                url: '/system/menu/menusZtree',
                elem: '#menuTree',
                setting: {
                    data:{
                        simpleData: {
                            enable: true,
                            idKey: "menuId",
                            pIdKey: "parentId",
                            rootPId: 0
                        },
                        key:{
                            name:"menuName",
                            url:""
                        }
                    },
                    check:{
                        chkStyle: "checkbox",
                        enable: true,
                        chkboxType:{"Y":"ps","N":"ps"}
                    }
                }
            }
        };

    studying.render(studyingConfig);
    /**
     * 复选框中事件联动
     */
    function formCheckboxEvent(obj) {
        switch (obj.elem.getAttribute("name")) {
            case "treeOp[fold_unfold]":
                foldEvent(this.checked);
                break;
            case "treeOp[both_none]":
                selectAll(this.checked);
                break;
            case "treeOp[linkage]":
                linkage(this.checked);
                break;
        }
    }

    function foldEvent(flag) {
        $.fn.zTree.getZTreeObj('menuTree').expandAll(flag);
    }

    function selectAll(flag) {
        $.fn.zTree.getZTreeObj('menuTree').checkAllNodes(flag);
    }

    function linkage(flag) {
        if (flag){
            $.fn.zTree.getZTreeObj('menuTree').setting.check.chkboxType = { "Y": "ps", "N": "ps" };
        }else{
            $.fn.zTree.getZTreeObj('menuTree').setting.check.chkboxType= { "Y": "", "N": "" };
        }
    }

    /**
     * 监听提交事件
     */
    function formSubmitEvent(obj){
        let params = {
            roleName: obj.field.roleName,
            roleKey: obj.field.roleKey,
            roleSort: obj.field.roleSort,
            status: $(obj.form).find("input[name='status']").is(':checked')?'0':'1',
            remark: obj.field.remark,
            menuIds: treeParams()
        };
        $.ajax({
            url: '/system/role/save',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
            }
        });
        parent.layui.layer.closeAll();
        return false;
    }

    function treeParams() {
        let nodes = $.fn.zTree.getZTreeObj('menuTree').getCheckedNodes(true);
        return nodes.map(item=>item.menuId);
    }
});