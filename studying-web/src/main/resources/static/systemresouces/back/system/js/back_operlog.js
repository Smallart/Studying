layui.config({
    base: '/expandjs/'
}).extend({
    xmSelect: 'xmSelect/xm-select',
    studying: 'studying/studying'
}).use(['jquery','layer','studying','xmSelect'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        studying = layui.studying,
        xmSelect = layui.xmSelect,
        studyingConfig = {
            form:{
                enable:true,
                events:{
                    submit:{
                        filter: '*',
                        event: submitEvent
                    }
                },
                dateTime:{
                    enable: true
                }
            },
            table:{
                enable: true,
                filter: 'operLogTable',
                options: {
                    elem:'#operLogTable',
                    height: 'full-260',
                    url:'/system/user/find',
                    cols:[[
                        {type:'checkbox',fixed:'left'},
                        {field:'userId',title:'Id',align:'center'},
                        {field:'loginName',title:'登录名称',sort:true,align: 'center',width: 110},
                        {field:'userName',title:'用户名称',align:'center',width:100},
                        {field:'deptName',title:'部门',align:'center',width:120},
                        {field:'iphone',title:'手机',align:'center',width:130},
                        {title:'用户状态',align:'center',width:100,templet: '#userStatus'},
                        {title:'创建时间',align:'center',sort:true,width:120,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd')}}</div>",field:'createTime'},
                        {title:'操作',fixed:'right',align:'center',toolbar:'#barOperator',width:250},
                    ]],
                    done:function () {
                        $('.btn-box').on('mouseenter mouseleave',function (event) {
                            if (event.type=='mouseenter'){
                                $(this).find('.more-operate').show();
                            }else if (event.type == 'mouseleave'){
                                $(this).find('.more-operate').hide();
                            }
                        });
                    }
                },
                events:{
                    checkbox:tableCheckBoxEvent,
                    tool: tableToolEvent,
                    sort: sort
                }
            },
            globalEvents:{
                enable: true,
                del: delEvent,
                reset: resetEvent
            }
        };

    // 搜索
    function submitEvent() {

    }


});