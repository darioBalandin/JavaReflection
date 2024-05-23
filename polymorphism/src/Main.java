/*
 *  MIT License
 *
 *  Copyright (c) 2020 Michael Pogrebinsky - Java Reflection - Master Class
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

import database.DatabaseClient;
import http.HttpClient;
import logging.FileLogger;
import udp.UdpClient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws Throwable {
        DatabaseClient databaseClient = new DatabaseClient();
        HttpClient httpClient1 = new HttpClient("123.456.789.0");
        HttpClient httpClient2 = new HttpClient("11.33.55.0");
        FileLogger fileLogger = new FileLogger();
        UdpClient udpClient = new UdpClient();

        String requestBody = "request data";

        List<Class<?>> methodParameterTypes = Arrays.asList(new Class<?>[]{String.class});

        Map<Object, Method> requestExecutors =
                groupExecutors(Arrays.asList(databaseClient, httpClient1, httpClient2, fileLogger, udpClient),
                        methodParameterTypes);

        executeAll(requestExecutors, requestBody);
    }

    public static void executeAll(Map<Object, Method> requestExecutors, String requestBody) {
        for (Map.Entry<Object, Method> entry : requestExecutors.entrySet()) {
            Object requestExecutor = entry.getKey();
            Method method = entry.getValue();
            try {
                method.invoke(requestExecutor,requestBody);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static Map<Object, Method> groupExecutors(List<Object> requestExecutors, List<Class<?>> methodParametersTypes) {

        Map<Object, Method> instanceToMethod = new HashMap<>();
        for (Object requestExecutor : requestExecutors) {
            Method[] declaredMethods = requestExecutor.getClass().getDeclaredMethods();

            for (Method method : declaredMethods) {
                if (Arrays.asList(method.getParameterTypes()).equals(methodParametersTypes)) {
                    instanceToMethod.put(requestExecutor, method);
                }
            }
        }
        return instanceToMethod;

    }


}
