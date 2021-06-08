layui.config({
    base: '/expandjs/'
}).extend({
    studyingPageCommon: 'studyingpagecommon/studyingPageCommon'
}).use(['jquery','studyingPageCommon'],function () {
    let $ = layui.jquery,
    studyingPageCommon = layui.studyingPageCommon,
    events={
        add:function () {
            layer.open({
                title: "添加岗位",
                type: 2,
                content: '/system/post/add',
                area:['600px','520px'],
                btn:['确定','关闭'],
                yes:function (index,layero) {

                },
                btn2: function () {

                }
            });
        }
    };

    init();
    function init() {
        initTable();
    }
    function initTable() {
        studyingPageCommon.render({
            initType: "table",
            table:{
                elem:'#deptTable',
                height: 'full-240',
                url:'/system/post/find',
                cols:[[
                    {type:'checkbox',fixed:'left'},
                    {field:'postId',title:'岗位编码',align:'center',sort:true},
                    {field:'postCode',title:'岗位名称',sort:true,align: 'center',width: 110,sort:true},
                    {field:'postSort',title:'显示顺序',align:'center',sort:true},
                    {title:'状态',align:'center',width:120,templet: `<a class="layui-btn layui-btn-radius layui-btn-xs {{d.status=='0'?'':'layui-btn-warm'}}">{{d.status==0?'正常':'停用'}}</a>`},
                    {title:'创建时间',align:'center',sort:true,width:120,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd HH:mm:ss')}}</div>"},
                    {title:'操作',fixed:'right',align:'center',toolbar:'#barOperator',width:250},
                ]]
            }
        })
    }

    $('body').on('click','*[studying-event]',function () {
        let eventName = $(this).attr('studying-event');
        eventName&&events[eventName].call(this);
    });
});