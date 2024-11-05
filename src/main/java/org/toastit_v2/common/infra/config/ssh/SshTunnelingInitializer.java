package org.toastit_v2.common.infra.config.ssh;

import static java.lang.System.exit;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import jakarta.annotation.PreDestroy;
import jakarta.validation.constraints.NotNull;
import java.util.Properties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Component
@Profile("dev")
public class SshTunnelingInitializer {

    private String host;

    private String user;

    private int sshPort;

    private String privateKeyPath;

    private Session session;

    public SshTunnelingInitializer(
            @NotNull @Value("${ssh.host}") String host,
            @NotNull @Value("${ssh.user}") String user,
            @NotNull @Value("${ssh.port}") int sshPort,
            @NotNull @Value("${ssh.private_key_path}") String privateKeyPath
    ) {
        this.host = host;
        this.user = user;
        this.sshPort = sshPort;
        this.privateKeyPath = privateKeyPath;
    }

    @PreDestroy
    public void closeSSH() {
        if (session.isConnected()) {
            session.disconnect();
            log.debug("SSH 연결이 성공적으로 종료되었습니다.");
        }
    }

    public Integer buildSshConnection(String databaseUrl, int databasePort) {

        Integer forwardedPort = null;

        try {
            log.debug("SSH 연결을 시작합니다: 사용자={}, 호스트={}, 포트={}, 개인 키 경로={}", user, host, sshPort, privateKeyPath);

            log.debug("SSH 터널링을 시작합니다...");
            JSch jSch = new JSch();

            log.debug("SSH 세션을 생성 중입니다...");
            jSch.addIdentity(privateKeyPath);
            session = jSch.getSession(user, host, sshPort);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            log.debug("SSH 세션이 생성되었습니다. 연결을 시도합니다...");
            session.connect();
            log.info("SSH 연결이 성공적으로 확립되었습니다.");

            log.debug("포트 포워딩을 시작합니다...");
            forwardedPort = session.setPortForwardingL(0, databaseUrl, databasePort);
            log.debug("데이터베이스에 성공적으로 연결되었습니다.");
        } catch (Exception e) {
            log.error("SSH 터널링을 설정하는 데 실패했습니다: {}", e.getMessage());
            closeSSH();
            exit(1);
        }

        return forwardedPort;
    }
}
