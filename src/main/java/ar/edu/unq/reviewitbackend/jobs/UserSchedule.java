package ar.edu.unq.reviewitbackend.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ar.edu.unq.reviewitbackend.controllers.CommonController;
import ar.edu.unq.reviewitbackend.entities.User;
import ar.edu.unq.reviewitbackend.services.UserService;

@Component
public class UserSchedule extends CommonController<User, UserService>{

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSchedule.class);

    @Scheduled(cron = "${cron.expression}")
    public void resetComplaintCountUsingScheduleTask() {
    	LOGGER.info("Proceso programado de cumplimiento de penalidad.");
    	try {
    		this.service.resetComplaintCountForSchedule();
    	}catch (Exception e) {
    		LOGGER.error("Error en el proceso programado de reseteo de contador de denuncias.", e);
    	}
    }
}
