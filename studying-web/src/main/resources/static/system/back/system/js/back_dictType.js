layui.config({
    base: '/expandjs/'
}).extend({
    studyingPageCommon: 'studyingpagecommon/studyingPageCommon'
}).use(['jquery','studyingPageCommon'],function(){
    let studyingPageCommon = layui.studyingPageCommon,
        $= layui.jquery,
        events={
            add:function () {
                layer.open({
                    title: "添加类别",
                    type: 2,
                    content: '/system/dict/dictType/add',
                    area:['600px','420px'],
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
        initDateAndTable();
    }

    function initDateAndTable() {
        studyingPageCommon.render({
            table:{
                elem:'#dictTypeTable',
                height: 'full-240',
                url:'/system/dict/type/find',
                cols:[[
                    {type:'checkbox',fixed:'left'},
                    {field:'dictId',title:'字典主键',align:'center',width:90},
                    {field:'dictName',title:'字典名称',align:'center',sort:true},
                    {title:'字典类型',align:'center',sort:true,width:150,templet:function (res) {
                            return `<a class="dictType" href="javaScript:void(0)">${res.dictType}</a>`;
                        }},
                    {title:'状态',align:'center',templet:function (res) {
                            return `<input type="checkbox" lay-skin="switch" lay-text="正常|停用" ${res.status==0?'checked':''}>`;
                        }},
                    {field:'remark',title:'备注',align:'center'},
                    {title:'创建时间',align:'center',sort:true,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd HH:mm:ss')}}</div>",width: 170},
                    {title:'操作',align:'center',toolbar:'#operatorButtons',width:200,fixed: 'right'},
                ]],
                done:function () {
                    $('.btn-box').on('mouseenter mouseleave',function (event) {
                        if (event.type=='mouseenter'){
                            $(this).find('.more-operate').show();
                        }else if (event.type == 'mouseleave'){
                            $(this).find('.more-operate').hide();
                        }
                    });
                    $("a").on('click',function (event) {
                        top.layui.pageOp.openTabsPage('/system/dict/detail/'+this.textContent,'字典数据');
                    });
                }
            }
        });
    }
    $('body').on('click','*[studying-event]',function () {
        let eventName = $(this).attr('studying-event');
        eventName&&events[eventName].call(this);
    });
});