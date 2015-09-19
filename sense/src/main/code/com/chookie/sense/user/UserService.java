package com.chookie.sense.user;

import com.chookie.sense.infrastructure.Service;

public class UserService implements Runnable {
    private final Service<TwitterUser> service;

    public UserService() {
        // TODO: create a new service that points to the twitter service,
        // and serves its own data on port 8083 and uri /users/
        service = null;
    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) {
        new UserService().run();
    }
}