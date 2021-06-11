layui.use(['jquery','table','form'],function () {
    let $  = layui.jquery,
        table = $.extend(layui.table,{
            config:{
                checkName:'flag'
            }
        }),
        form = layui.form,
        userId = $('input[name="userId"]').val();
    
    init();
    function init() {
        initInput();
        initTable();
    }

    /**
     * 初始化Input
     */
    function initInput() {
        $.get('/system/user/assignRoleInput/'+userId,function (data) {
            form.val("assignRoleForm",{
                "userName":data.userName,
                "loginName":data.loginName
            });
        });
    }

    /**
     * 初始化Table值
     */
    function initTable() {
        table.render({
            elem:'#roleTable',
            height: 'full-350',
            url:'/system/role/assignRoleTable/'+userId,
            cols:[[
                {type:'checkbox',fixed:'left'},
                {field:'roleId',title:'角色编号',align:'center'},
                {field:'roleName',title:'角色名称',align:'center',sort:true},
                {field:'roleKey',title:'权限字符',align:'center',sort:true,sort:true},
                {title:'创建时间',align:'center',sort:true,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd')}}</div>"}
            ]],
            parseData:function (res) {
                return{
                    "code":res.code,
                    "msg":res.msg,
                    "count":res.data.total,
                    "data": res.data.data
                }
            },
            request:{
                pageName: 'limit',
                limitName: 'offset'
            },
            skin: 'line',
            done:function (res) {
                console.log(res);
            }
        })
    }

    /**
     * 表单提交
     */
    form.on('submit(*)',function (obj) {
        let params = {};
        params.userId = parseInt(obj.field.userId);
        let roles = table.checkStatus('roleTable');
        let ids = roles.data.map(item=>parseInt(item.roleId));
        params.roles = ids;
        $.ajax({
            url: '/system/user/assignRoles',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
                table.reload({
                    url:'/system/role/assignRoleTable/'+userId
                })
            }
        });
        return false;
    });
});