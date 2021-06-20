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
                        event: formSubmit
                    },
                    select:{
                        filter: 'dataScope',
                        event: formSelectEvent
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
                elem: '#companyTree',
                setting: {
                    async:{
                        enable: true,
                        type: 'get',
                        url:'/system/dept/dataScope/'+roleId
                    },
                    data:{
                        key:{
                            name: 'departmentName',
                            url:""
                        },
                        simpleData:{
                            enable: true,
                            idKey: "departmentId",
                            pIdKey: "parentId",
                            rootPId: 0
                        }
                    },
                    callback:{
                        onAsyncSuccess:function () {
                            $.fn.zTree.getZTreeObj("companyTree").expandAll(true);
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
        deptTree= $('#deptTree');

    studying.render(studyingConfig);

    /**
     * 表单 select状态改变事件
     */
    function formSelectEvent(data){
        if (data.value == '2'){
            deptTree.show();
        }else{
            deptTree.hide();
        }
    }

    /**
     * 数据权限checkbox事件监听
     */
    function formCheckboxEvent(obj){
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
        $.fn.zTree.getZTreeObj("companyTree").expandAll(flag);
    }

    function selectAll(flag) {
        $.fn.zTree.getZTreeObj("companyTree").checkAllNodes(flag);
    }

    function linkage(flag) {
        if (flag){
            $.fn.zTree.getZTreeObj("companyTree").setting.check.chkboxType = { "Y": "ps", "N": "ps" };
        }else{
            $.fn.zTree.getZTreeObj("companyTree").setting.check.chkboxType= { "Y": "", "N": "" };
        }
    }

    /**
     * 提交
     */
    function formSubmit(obj) {
        let params={
            roleId: obj.field.roleId,
            roleName: obj.field.roleName,
            dataScope: obj.field.dataScope,
            deptIds: deptIds()
        };
        $.ajax({
            url: '/system/role/dataScope',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
                parent.layui.layer.closeAll();
            }
        });
    }

    function deptIds() {
        let nodes = $.fn.zTree.getZTreeObj("companyTree").getCheckedNodes(true);
        return nodes.map(item=>item.departmentId);
    }
});