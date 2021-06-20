layui.config({
    base: '/expandjs/'
}).extend({
    JqueryZtree: 'ztree/jquery-ztree'
}).define(['jquery','form','table','JqueryZtree','laydate'],function (exports) {
    let $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        laydate = layui.laydate,
        Studying = function () {
            this.v= '1.0'
        },_MOD='studying',
        default_setting={
            //表单
            form:{
                enable:false,
                filter: null,
                //时间组件初始化
                dateTime:{
                    enable:false,
                    startTimeElem:'#create-start-time',
                    endTimeElem:'#create-end-time',
                    format: 'yyyy-MM-dd HH:mm:ss'
                },
                //相关事件
                events: null,
                // 表单验证规则
                verify:{
                    enable:false,
                    rule:null
                }
            },
            //表格
            table:{
                enable:false,
                filter: null,
                // 相关设置
                options:{
                    elem: undefined,
                    height: undefined,
                    url: undefined,
                    request:{
                        pageName: 'limit',
                        limitName: 'offset'
                    },
                    page: true,
                    skin: 'line',
                    cols: undefined,
                    parseData:function (res) {
                        return{
                            "code":res.code,
                            "msg":res.msg,
                            "count":res.data.total,
                            "data": res.data.data
                        }
                    },
                    done: undefined
                },
                //相关事件
                events: null
            },
            //全局事件
            globalEvents:{
                enable: false,
                add: null,
                edit: null,
                del: null,
            },
            //ztree树
            ztree:{
                enable:false,
                elem: null,
                url: null,
                setting: null
            }
        };

    /**
     * 初始化
     */
    Studying.prototype.init = function(options){
        this._options=_options= $.extend(true,default_setting,options);
        let a = {
            initForm:function () {
                let form = _options['form'];
                if (!form.enable) return a;
                this.initFormDate(form.dateTime).initFormVerify(form);
                return a;
            },
            initFormDate:function (dateTimeConfig) {
                if (!dateTimeConfig.enable) return a;
                let startTime = laydate.render({
                    elem: dateTimeConfig.startTimeElem,
                    type: 'datetime',
                    form: dateTimeConfig.dateForm,
                    done:function (value,dates,endDate) {
                        endTime.config.min = {
                            year: dates.year,
                            month: dates.month - 1,
                            date: dates.date
                        };
                    }
                });
                let endTime = laydate.render({
                    elem: dateTimeConfig.endTimeElem,
                    type: 'datetime',
                    form: dateTimeConfig.dateForm,
                    done:function (value,dates,endDate) {
                        startTime.config.max = {
                            year: dates.year,
                            month: dates.month - 1,
                            date: dates.date
                        };
                    }
                });
                return a;
            },
            initFormVerify:function(formConfig){
                let verifyConfig = formConfig['verify'];
                if (!verifyConfig.enable) return a;
                form.verify(verifyConfig.rule)
            },
            initTable:function () {
                let tableConfig = _options['table'];
                if (!tableConfig.enable) return a;
                table.render(tableConfig.options);
                return a;
            },
            initTree:function () {
                let ztreeConfig = _options['ztree'];
                if (!ztreeConfig.enable) return a;
                if (ztreeConfig.url!=null){
                    $.get(ztreeConfig.url,function (data) {
                        $.fn.zTree.init($(ztreeConfig.elem),ztreeConfig.setting,data);
                    });
                }else{
                    $.fn.zTree.init($(ztreeConfig.elem),ztreeConfig.setting);
                }
                return a;
            }
        };
        a.initTree().initForm().initTable();
        this.bindEvent();
    };

    /**
     * 绑定事件
     */
    Studying.prototype.bindEvent = function(){
        // 全局事件监听
        let globalEventsConfig=  this._options.globalEvents;
        let $this = this;
        if (globalEventsConfig.enable){
            $('body').on('click','*[studying-event]',function () {
                let name = $(this).attr('studying-event');
                name&&default_setting.globalEvents[name].call();
            });
        }
        // 表单事件监听
        let formEvents = this._options.form.events;
        if (formEvents!=null|undefined){
            for (let field of Object.keys(formEvents)){
                form.on(field+'('+formEvents[field].filter+')',formEvents[field].event);
            }
        }

        // 表格事件监听
        let tableConfig = this._options.table;
        let tableEvents = tableConfig.events;
        if (tableEvents!=null|undefined){
            let filter = tableConfig.filter;
            for (let methodName of Object.keys(tableEvents)){
                table.on(methodName+'('+filter+')',tableEvents[methodName]);
            }
        };
    };

    let obj = {
        render:function (options) {
            let s = new Studying();
            s.init(options);
        },
        form:function () {
            return form;
        },
        table:function () {
            return table;
        }
    };

    exports(_MOD,obj);
});