layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','layer','studying'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        studying = layui.studying,
        roleId = $('input[name="roleId"]').val(),
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
                }
            },
            ztree:{
                enable:true,
                url: '/system/role/edit/menuTree/'+roleId,
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
        },
        form = studying.form();

    studying.render(studyingConfig);
    init();
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
            roleId: roleId,
            roleName: obj.field.roleName,
            roleKey: obj.field.roleKey,
            roleSort: obj.field.roleSort,
            status: $(obj.form).find("input[name='status']").is(':checked')?'0':'1',
            remark: obj.field.remark,
            menuIds: treeParams()
        };
        $.ajax({
            url: '/system/role/edit',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
                if(data.code == 0){
                    closecurrent();
                }
            }
        });

        return false;
    }

    function closecurrent() {
        setTimeout(function(){
            var index = index = parent.layer.getFrameIndex(window.name);
            parent.window.location.reload();
            parent.layer.close(index);
        },1000);
    }

    function treeParams() {
        let nodes = $.fn.zTree.getZTreeObj('menuTree').getCheckedNodes(true);
        return nodes.map(item=>item.menuId);
    }

    function init() {
        initValue();
    }

    /**
     * 初始化数据
     */
    function initValue() {
        $.get('/system/role/edit/'+roleId,function (obj) {
            if (obj.code==0){
                form.val("roleForm",{
                    "roleName": obj.data.roleName,
                    "roleKey": obj.data.roleKey,
                    "roleSort": obj.data.roleSort,
                    "status": obj.data.status=='0'?$('input[name="status"]').prop("checked",true):'',
                    "remark": obj.data.remark
                });
            }
        });
    }
});