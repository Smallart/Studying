layui.use(['jquery','table','form'],function(){
    let table = layui.table,
        $= layui.jquery,
        form = layui.form,
        events={
            add:function () {
                let dictType = $('button[studying-event="add"]').attr('dictType');
                layer.open({
                    title: "添加类别",
                    type: 2,
                    content: '/system/dict/dictDetail/add?dictType='+dictType,
                    area:['600px','620px'],
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
        table.init('dictDetailTable',{
            height: 'full-240',
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
            skin: 'line'
        });
    }

    table.on('checkbox(dictDetailTable)',function (obj) {
        reloadTable();
    });

    function reloadTable(params){
        table.reload('dictDetailTable',{
            url:'/system/dict/detail/find',
            where: params,
            cols:[[
                {type:'checkbox',fixed:'left'},
                {field:'dictCode',title:'字典编码',align:'center'},
                {field:'dictLabel',title:'字典标签',align: 'center',width: 110},
                {field:'dictValue',title:'字典键值',align:'center'},
                {field:'dictSort',title:'字典排序',align:'center'},
                {title:'状态',align:'center',width:80,templet: '#status'},
                {field:'remark',title:'备注',align:'center'},
                {title:'创建时间',align:'center',sort:true,width:250,templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd HH:mm:ss')}}</div>"},
                {title:'操作',fixed:'right',align:'center',toolbar:'#operatorButtons',width:140}
            ]]
        });
    }

    form.on('submit(*)',function (obj) {
        let params = {
            dictDetailStatus: obj.field.dictDetailStatus,
            dictLabel: obj.field.dictLabel,
            dictType: obj.field.dictType
        };
        reloadTable(params);
        return false;
    });

    $('body').on('click','*[studying-event]',function () {
        let eventName = $(this).attr('studying-event');
        eventName&&events[eventName].call(this);
    });
});