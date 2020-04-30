package com.tobiassteely.s3put;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.tobiassteely.s3put.api.Data;
import com.tobiassteely.s3put.api.Log;
import com.tobiassteely.s3put.api.config.Config;
import com.tobiassteely.s3put.api.config.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class S3Put {

    public static void main(String[] args) {

        ConfigManager configManager = new ConfigManager();
        Config settings = configManager.getConfig("settings.json");
        settings.loadDefault("accesskey", "key");
        settings.loadDefault("secretkey", "key");
        settings.loadDefault("endpoint", "default");
        settings.loadDefault("folder", "none");
        settings.loadDefault("bucket", "bucket");
        settings.loadDefault("region", "US_EAST_2");
        settings.save();

        String accessKey = settings.getString("accesskey");
        String secretKey = settings.getString("secretkey");
        String folder = settings.getString("folder");
        String bucket = settings.getString("bucket");
        String regionName = settings.getString("region");
        String endpoint = settings.getString("endpoint");

        if(accessKey.equalsIgnoreCase("key")) {
            Log.sendMessage(2, "Please set your access key.");
            return;
        } else if(secretKey.equalsIgnoreCase("key")) {
            Log.sendMessage(2, "Please set your secret key.");
            return;
        } else if(bucket.equalsIgnoreCase("bucket")) {
            Log.sendMessage(2, "Please set your bucket.");
            return;
        }

        if(args.length >= 1) {

            String fileName = new Data().parseStringArrayNoDelimiter(0, args);

            AWSCredentials credentials = new BasicAWSCredentials(
                    accessKey,
                    secretKey
            );

            Regions region = Regions.fromName(regionName);
            if(region == null) {
                region = Regions.DEFAULT_REGION;
            }

            AmazonS3 s3client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withRegion(region)
                    .build();



            if(!endpoint.equalsIgnoreCase("default")) {
                s3client.setEndpoint(endpoint);
            }

            File file = new File(fileName);
            if(file.exists()) {
                if(file.isDirectory()) {
                    try {
                        Stream<Path> paths = Files.walk(file.toPath());
                        paths.forEach((Path path) -> {
                            if (folder.equalsIgnoreCase("none")) {
                                s3client.putObject(bucket, fileName, path.toFile());
                            } else {
                                s3client.putObject(bucket, folder + System.getProperty("file.separator") + fileName, path.toFile());
                            }
                            Log.sendMessage(0, "Uploading file " + path.toString() + " from folder " + file.toString());
                        });
                    } catch (IOException ex) {
                        Log.sendMessage(0, "Failed to upload folder " + file.toString());
                        ex.printStackTrace();
                    }
                } else {
                    if (folder.equalsIgnoreCase("none")) {
                        s3client.putObject(bucket, fileName, new File(fileName));
                    } else {
                        s3client.putObject(bucket, folder + System.getProperty("file.separator") + fileName, new File(fileName));
                    }
                    Log.sendMessage(0, "Uploading file " + file.toString());
                }
            }
        } else {
            Log.sendMessage(2, "Please specify a file! \"java -jar s3put.jar <file:folder>\"");
        }
    }

}
