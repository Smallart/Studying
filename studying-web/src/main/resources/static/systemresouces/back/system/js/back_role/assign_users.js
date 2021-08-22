layui.config({
    base: '/expandjs/'
}).extend({
    studying: 'studying/studying'
}).use(['jquery','layer','studying'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        studying = layui.studying,
        table = studying.table(),
        form = studying.form(),
        roleId = $('input[name="roleId"]').val(),
        studyingConfig = {
            form:{
                enable:true,
                events:{
                    submit:{
                        filter: '*',
                        event: submitEvent
                    }
                }
            },
            table:{
                enable: true,
                filter: 'userTable',
                options: {
                    elem:'#userTable',
                    height: 'full-220',
                    url:'/system/user/findBindUserByRoleId/'+roleId,
                    cols:[[
                        {type:'checkbox',fixed:'left'},
                        {field:'loginName',title:'登录名称',sort:true,align: 'center',width: 110},
                        {field:'userName',title:'用户名称',align:'center'},
                        {field:'email',title:'部门',align:'center'},
                        {field:'iphone',title:'手机',align:'center'},
                        {title:'用户状态',align:'center',width:100,templet: function (res) {
                                return `<a class="layui-btn layui-btn-primary layui-btn-xs layui-btn-radius ${res.status==0?'layui-border-blue':'layui-border-red'}">${res.status==0?'正常':'停用'}</a>`;
                            }},
                        {title:'创建时间',align:'center',sort:true,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd')}}</div>"},
                        {title:'操作',fixed:'right',align:'center',toolbar:'#barOperator'},
                    ]],
                    parseData:function (res) {
                        return{
                            "code":res.code,
                            "msg":res.msg,
                            "count":res.data.total,
                            "data": res.data.data
                        }
                    }
                },
                events:{
                    checkbox:tableCheckboxEvent,
                    tool: tableToolEvent
                }
            },
            globalEvents:{
                enable: true,
                add: addEvent,
                batchCancelAuth: cancelAuthAllUserEvent
            }
        },
        batchCancel = $('#batchCancel');

    studying.render(studyingConfig);

    /**
     * table的复选框事件
     */
    function tableCheckboxEvent() {
        let datas = table.checkStatus('userTable');
        if (datas.data.length>0){
            batchCancel.removeClass('layui-btn-disabled')
        }else{
            batchCancel.addClass('layui-btn-disabled');
        }
    }

    /**
     * table tool事件
     * @param obj
     */
    function tableToolEvent(obj) {
        layer.confirm('是否取消选中用户的授权？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            let params = {
                userId: obj.data.userId,
                roleId: roleId
            };
            $.ajax({
                url: '/system/role/cancelAuthUser/',
                type: 'post',
                data: JSON.stringify(params),
                headers:{
                    'Content-Type': 'application/json'
                },
                success:function (data) {
                    layer.msg(data.msg);
                    reloadTable(getParams());
                }
            });
        });
    }

    /**
     * 表单提交事件
     * @param obj
     */
    function submitEvent(obj) {
        let param = {
            loginName: obj.field.loginName,
            iphone: obj.field.iphone
        };
        reloadTable(param);
    }

    /**
     * 全局add事件
     */
    function addEvent() {
        layer.open({
            title: "添加用户",
            type: 2,
            content: '/system/role/assignUsers/add/'+roleId,
            area:['870px','400px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                //这个应该是直接获得了子窗口的东西
                let obj = layero.find('iframe')[0].contentWindow.layui.table.checkStatus('userTable');
                let userIds = obj.data.map(item=>item.userId).join(",");
                $.ajax({
                    url: '/system/role/assignUser/'+roleId,
                    type: 'post',
                    data: userIds,
                    headers:{
                        'Content-Type': 'application/json'
                    },
                    success:function (data) {
                        layer.msg(data.msg);
                        layer.close(index);
                        reloadTable(getParams());
                    }
                });
            }
        });
    }

    /**
     * 全局事件 批量取消授权
     */
    function cancelAuthAllUserEvent() {
        let objs = table.checkStatus('userTable');
        if (objs.data.length<1){
            layer.msg("请选择至少一位用户");
            return
        }
        layer.confirm('是否批量取消选中用户的授权？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            let userIds = objs.data.map(item=>item.userId).join(",");
            $.ajax({
                url: '/system/role/cancelAuthAllUser/'+roleId,
                type: 'post',
                data: userIds,
                headers:{
                    'Content-Type': 'application/json'
                },
                success:function (data) {
                    layer.msg(data.msg);
                }
            });
        });
    }

    /**
     * 重载table
     * @param param
     */
    function reloadTable(param){
        table.reload('userTable',{
            url: '/system/user/findBindUserByRoleId/'+roleId,
            where: param
        });
    }

    /**
     * 获得参数
     * @returns {{loginName: *, iphone: *}}
     */
    function getParams(){
        let data = form.val('assign_userForm');
        let params = {
            loginName:data.loginName,
            iphone: data.iphone
        };
        return params;
    }
});