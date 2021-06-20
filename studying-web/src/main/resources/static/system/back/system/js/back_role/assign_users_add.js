layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','layer','studying'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        studying = layui.studying,
        roleId = $('input[name="roleId"]').val(),
        table = studying.table(),
        studyingConfig = {
            form:{
                enable:true,
                events:{
                    submit:{
                        filter: '*',
                        event: formSubmitEvent
                    }
                }
            },
            table:{
                enable: true,
                filter: 'userTable',
                options: {
                    elem:'#userTable',
                    height: 'full-220',
                    url:'/system/user/findNotBindUserByRoleId/'+roleId,
                    cols:[[
                        {type:'checkbox',fixed:'left'},
                        {field:'loginName',title:'登录名称',sort:true,align: 'center',width: 110},
                        {field:'userName',title:'用户名称',align:'center'},
                        {field:'email',title:'部门',align:'center'},
                        {field:'iphone',title:'手机',align:'center'},
                        {title:'用户状态',align:'center',width:100,templet: function (res) {
                                return `<a class="layui-btn layui-btn-primary layui-btn-xs layui-btn-radius ${res.status==0?'layui-border-blue':'layui-border-red'}">${res.status==0?'正常':'停用'}</a>`;
                            }},
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
                    page: true,
                }
            },
            globalEvents:{
                enable: true,
                reset: reset
            }
        };

    studying.render(studyingConfig);
    /**
     * 重载table
     * @param params
     */
    function reloadTable(params){
        table.reload('userTable',{
            url:'/system/user/findNotBindUserByRoleId/'+roleId,
            where: params
        });
    }

    /**
     * 表单提交事件
     */
    function formSubmitEvent(data){
        let params = {
            loginName: data.field.loginName,
            iphone: data.field.iphone
        };
        reloadTable(params);
    }

    /**
     * 全局reset事件
     */
    function reset() {
        reloadTable(null);
    }
});