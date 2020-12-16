package com.lwk.myspring.hive.main;

import org.apache.hive.service.auth.PlainSaslHelper;
import org.apache.hive.service.cli.OperationHandle;
import org.apache.hive.service.cli.RowSet;
import org.apache.hive.service.cli.SessionHandle;
import org.apache.hive.service.cli.thrift.ThriftCLIServiceClient;
import org.apache.hive.service.rpc.thrift.*;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

/**
 * @author kai
 * @date 2020-08-01 0:15
 */
public class HiveThrift {

    public static void main(String[] args) throws Exception {


        TTransport transport = new TSocket("192.168.99.100", 10000);
        transport = PlainSaslHelper.getPlainTransport("root", "123456", transport);
        TProtocol protocol = new TBinaryProtocol(transport);
        transport.open();
        ThriftCLIServiceClient serviceClient = new ThriftCLIServiceClient(new TCLIService.Client(protocol));
        SessionHandle sessionHandle = serviceClient.openSession("root", "123456");
        OperationHandle operationHandle = serviceClient.executeStatement(sessionHandle, "dfs -ls /", null);
        RowSet results = serviceClient.fetchResults(operationHandle);

        for (Object[] result : results) {
            System.out.println(Arrays.asList(result));
        }
    }
}
