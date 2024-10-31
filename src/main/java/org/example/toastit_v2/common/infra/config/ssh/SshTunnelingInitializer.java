package org.example.toastit_v2.common.infra.config.ssh;

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
@Profile("dev")
@Component
@ConfigurationProperties(prefix = "ssh")
@Validated
@Setter
public class SshTunnelingInitializer {

    @NotNull
    @Value("${ssh.host}")
    private String host;

    @NotNull
    @Value("${ssh.user}")
    private String user;

    @NotNull
    @Value("${ssh.port}")
    private int sshPort;

    @NotNull
    @Value("${ssh.private_key_path}")
    private String privateKeyPath;

    private Session session;

    @PreDestroy
    public void closeSSH() {

        if (session.isConnected()) {
            session.disconnect();
        }
    }

    public Integer buildSshConnection(String databaseUrl, int databasePort) {

        Integer forwardedPort = null;

        try {
            log.debug("Connecting to SSH: {}@{}:{} with privateKeyPath", user, host, sshPort);

            log.debug("Starting SSH tunneling...");
            JSch jSch = new JSch();

            log.debug("Creating SSH session...");
            jSch.addIdentity(privateKeyPath);
            session = jSch.getSession(user, host, sshPort);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            log.debug("SSH session created successfully. Connecting...");
            session.connect();
            log.info("SSH connection established successfully.");

            log.debug("Starting port forwarding...");
            forwardedPort = session.setPortForwardingL(0, databaseUrl, databasePort);
            log.debug("Successfully connected to database.");

        } catch (Exception e) {
            log.error("Failed to establish SSH tunneling: {}", e.getMessage());
            closeSSH();
            exit(1);
        }

        return forwardedPort;
    }
}