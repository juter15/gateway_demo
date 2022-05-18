package com.example.gateway_demo;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DeploymentMode;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
//import org.apache.ignite.springdata22.repository.config.EnableIgniteRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@EnableFeignClients
@SpringBootApplication
//@EnableIgniteRepositories
public class GatewayDemoApplication {
    @Bean
    public Ignite igniteInstance() {
        //System.setProperty("IGNITE_JETTY_PORT","8888");
        org.apache.ignite.configuration.IgniteConfiguration cfg = new org.apache.ignite.configuration.IgniteConfiguration()
                // Enabling peer-class loading feature.
                .setPeerClassLoadingEnabled(true)
                .setDeploymentMode(DeploymentMode.CONTINUOUS)
                /*클라이언트 노드 설정*/
                .setClientMode(true)
                ;

        // Discovery SPI

        TcpDiscoverySpi spi = new TcpDiscoverySpi();

        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        // Set initial IP addresses.
        // Note that you can optionally specify a port or a port range.
        //ipFinder.setAddresses(Arrays.asList("localhost", "localhost:47500..47509"));
        //spi.setIpFinder(ipFinder);

        TcpDiscoveryMulticastIpFinder tcpDiscoveryMulticastIpFinder = new TcpDiscoveryMulticastIpFinder();
        tcpDiscoveryMulticastIpFinder.setAddresses(Arrays.asList("localhost", "localhost:47500..47509"));
        //tcpDiscoveryMulticastIpFinder.set
        //tcpDiscoveryMulticastIpFinder.setAddresses(Arrays.asList("192.168.0.109:47500..47509","192.168.0.111:47500..47509","192.168.0.112:47500..47509"));
        spi.setIpFinder(tcpDiscoveryMulticastIpFinder);
        //spi.setReconnectDelay(500);



        // Override default discovery SPI.
        cfg.setDiscoverySpi(spi);

        Ignite ignite = Ignition.start(cfg);




        return ignite;
    }
    public static void main(String[] args) {
        SpringApplication.run(GatewayDemoApplication.class, args);


    }

}
