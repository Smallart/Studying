layui.config({
    base: '../../../expandjs/'
}).extend({
    echarts: 'echarts/echarts'
}).use(['echarts'],function () {
    var echarts = layui.echarts;

    init();

    function init() {
        initBarChart();
        initLineChart();
    }

    function initBarChart() {
        var myChart = echarts.init(document.querySelector(".bar .chart"));
        var option = {
            color: ["#2f89cf"],
            tooltip: {
                trigger: "axis",
                axisPointer: {
                    // 坐标轴指示器，坐标轴触发有效
                    type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: "0%",
                top: "10px",
                right: "0%",
                bottom: "4%",
                containLabel: true
            },
            xAxis: [
                {
                    type: "category",
                    data: [
                        "IT行业",
                        "教育培训",
                        "游戏行业",
                        "医疗行业",
                        "电商行业",
                        "社交行业",
                        "金融行业"
                    ],
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLabel: {
                        textStyle: {
                            color: "rgba(255,255,255,.6)",
                            fontSize: "12"
                        }
                    },
                    axisLine: {
                        show: false
                    }
                }
            ],
            yAxis: [
                {
                    type: "value",
                    axisLabel: {
                        textStyle: {
                            color: "rgba(255,255,255,.6)",
                            fontSize: "12"
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: "rgba(255,255,255,.1)"
                            // width: 1,
                            // type: "solid"
                        }
                    },
                    splitLine: {
                        lineStyle: {
                            color: "rgba(255,255,255,.1)"
                        }
                    }
                }
            ],
            series: [
                {
                    name: "直接访问",
                    type: "bar",
                    barWidth: "35%",
                    data: [200, 300, 300, 900, 1500, 1200, 600],
                    itemStyle: {
                        barBorderRadius: 5
                    }
                }
            ]
        };
        myChart.setOption(option);
    }

    function initLineChart(){
        var myChart = echarts.init(document.querySelector(".line .chart"));
        var data = {
            year: [
                [24, 40, 101, 134, 90, 230, 210, 230, 120, 230, 210, 120],
                [40, 64, 191, 324, 290, 330, 310, 213, 180, 200, 180, 79]
            ]
        };
        var option = {
            color: ["#00f2f1", "#ed3f35"],
            tooltip: {
                // 通过坐标轴来触发
                trigger: "axis"
            },
            legend: {
                // 距离容器10%
                right: "10%",
                // 修饰图例文字的颜色
                textStyle: {
                    color: "#4c9bfd"
                }
                // 如果series 里面设置了name，此时图例组件的data可以省略
                // data: ["邮件营销", "联盟广告"]
            },
            grid: {
                top: "20%",
                left: "3%",
                right: "4%",
                bottom: "3%",
                show: true,
                borderColor: "#012f4a",
                containLabel: true
            },

            xAxis: {
                type: "category",
                boundaryGap: false,
                data: [
                    "1月",
                    "2月",
                    "3月",
                    "4月",
                    "5月",
                    "6月",
                    "7月",
                    "8月",
                    "9月",
                    "10月",
                    "11月",
                    "12月"
                ],
                // 去除刻度
                axisTick: {
                    show: false
                },
                // 修饰刻度标签的颜色
                axisLabel: {
                    color: "rgba(255,255,255,.7)"
                },
                // 去除x坐标轴的颜色
                axisLine: {
                    show: false
                }
            },
            yAxis: {
                type: "value",
                // 去除刻度
                axisTick: {
                    show: false
                },
                // 修饰刻度标签的颜色
                axisLabel: {
                    color: "rgba(255,255,255,.7)"
                },
                // 修改y轴分割线的颜色
                splitLine: {
                    lineStyle: {
                        color: "#012f4a"
                    }
                }
            },
            series: [
                {
                    name: "新增粉丝",
                    type: "line",
                    stack: "总量",
                    // 是否让线条圆滑显示
                    smooth: true,
                    data: data.year[0]
                },
                {
                    name: "新增游客",
                    type: "line",
                    stack: "总量",
                    smooth: true,
                    data: data.year[1]
                }
            ]
        };
        myChart.setOption(option);
    }
});