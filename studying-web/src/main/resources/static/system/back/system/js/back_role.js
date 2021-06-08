layui.config({
    base: '/expandjs/'
}).extend({
    studyingPageCommon: 'studyingpagecommon/studyingPageCommon'
}).use(['jquery','studyingPageCommon','layer'],function(){
    let studyingPageCommon = layui.studyingPageCommon,
        $= layui.jquery,
        layer = layui.layer,
        events = {
            add:function () {
                layer.open({
                    title: "添加角色",
                    type: 2,
                    content: '/system/role/add',
                    area:['600px','600px'],
                    btn:['确定','关闭'],
                    yes:function (index,layero) {
                        //这个应该是直接获得了子窗口的东西
                        // layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                        //关闭窗口
                        // layer.close(index);
                    },
                    btn2: function () {

                    }
                });
            }
        };
    init();

    function init() {
        initDateAndTable();
    }

    function initDateAndTable() {
        studyingPageCommon.render({
            table:{
                elem:'#roleTable',
                height: 'full-240',
                url:'/system/role/find',
                cols:[[
                    {type:'checkbox',fixed:'left'},
                    {field:'roleId',title:'角色编号',align:'center'},
                    {field:'roleName',title:'角色名称',align:'center',sort:true},
                    {field:'roleKey',title:'权限字符',align:'center',sort:true},
                    {field:'roleSort',title:'显示顺序',align:'center',sort:true},
                    {title:'角色状态',align:'center',templet:function (res) {
                            return `<input type="checkbox" lay-skin="switch" lay-text="正常|停用" ${res.status==0?'checked':''}>`;
                    }},
                    {title:'创建时间',align:'center',sort:true,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd')}}</div>"},
                    {title:'操作',align:'center',toolbar:'#barOperator',width:250},
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
            }
        });
    }
    
    $('body').on('click','*[studying-event]',function () {
        let eventName = $(this).attr('studying-event');
        eventName&&events[eventName].call(this);
    })
});