package edu.pnu.domain;

import java.text.SimpleDateFormat;

import edu.pnu.config.AppWebSocketConfig;
import edu.pnu.config.SpringContext;
import jakarta.persistence.PostPersist;

//@Component
public class AlertListener {
	@PostPersist
	public void onPostPersist(Alert alert) {
		// AppWebSocketConfig 빈을 SpringContext에서 가져옵니다.
		AppWebSocketConfig webSocketConfig = SpringContext.getBean(AppWebSocketConfig.class);
		System.out.println(alert.toString());

		String alertType = alert.getAlertType().equals(AlertType.ACTIVITY) ? "활동 자제" 
						: "공해 조절";
			
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월 dd일 a hh:mm");
		String msg = alert.getRegion().getLarge() + " " + alert.getRegion().getMiddle() + " " 
					+ alert.getRegion().getSmall() + " - " + sdf.format(alert.getAlertTime())
					+ alertType + "필요가 예상됩니다.";
		System.out.println(msg);
		if (webSocketConfig != null) {
			System.out.println("Sending message: " + msg);
//			webSocketConfig.sendPushMessage(alert.getRegion().getRegionId(), msg);
			webSocketConfig.sendPushMessage(alert.getRegion().getRegionId(), alert);
		} else {
			System.err.println("WebSocketConfig is null");
		}
	}
}
