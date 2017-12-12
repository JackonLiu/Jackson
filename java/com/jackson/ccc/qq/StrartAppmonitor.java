/*
package com.jackson.ccc.qq;

*/
/**
 * Created by LXP on 17-4-13.
 *//*


public class StrartAppmonitor {
    // 新建监控接口对象 StatAppMonitor monitor = new StatAppMonitor("ping:www.qq.com"); String ip = "www.qq.com"; Runtime run = Runtime.getRuntime(); java.lang.Process proc = null; try {  String str = "ping -c 3 -i 0.2 -W 1 " + ip;
    //Android 统计 SDK 开发者使用指南  long starttime = System.currentTimeMillis();  // 被监控的接口  proc = run.exec(str);  proc.waitFor();  long difftime = System.currentTimeMillis() - starttime;  // 设置接口耗时  monitor.setMillisecondsConsume(difftime);  int retCode = proc.waitFor();  // 设置接口返回码  monitor.setReturnCode(retCode);  // 设置请求包大小，若有的话  monitor.setReqSize(1000);  // 设置响应包大小，若有的话  monitor.setRespSize(2000);  // 设置抽样率 // 默认为 1，表示 100%。 // 如果是 50%，则填 2(100/50)，如果是 25%，则填 4(100/25)，以此类推。 monitor.setSampling(2);  if (retCode == 0) {
    // logger.debug("ping 连接成功");
    // 标记为成功   monitor.setResultType(StatAppMonitor.SUCCESS_RESULT_TYPE);  } else {
    //logger.debug("ping 测试失败");
    // 标记为逻辑失败，可能由网络未连接等原因引起的 // 但对于业务来说不是致命的，是可容忍的   monitor.setResultType(StatAppMonitor.LOGIC_FAILURE_RESULT_TYPE);  } } catch (Exception e) {  logger.e(e);  // 接口调用出现异常，致命的，标识为失败  monitor.setResultType(StatAppMonitor.FAILURE_RESULT_TYPE); } finally {  proc.destroy(); } // 上报接口监控 StatService.reportAppMonitorStat(ctx, monitor);
}
*/
