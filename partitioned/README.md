<!--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->

# Geode partitioned region example

This basic example demonstrates the basic properties of partitioned regions: 

- Data entries are distributed across all servers that host a region.
The distribution is like database sharding, except that the distribution
occurs automatically. It is also similar to data striping on disks,
except that the distribution is not based on hardware.

- Hashing distributes entries among buckets that reside on servers.
A good hash code is important in order to spread the entries among buckets.

In this example,
two servers host two partitioned regions. 
There is no redundancy, so that the basic properties of partitioning
may be observed.
The Producer code puts the same 10 entries into each of the two
partitioned regions.
The Consumer gets and prints the entries from each of the two regions.
Due to partitioning,
the entries are distributed among the two servers hosting the region.
Since there is no redundancy of the data within the region,
when one of the servers goes away,
the entries hosted within that server are also gone.

The two regions are the same, except for the hash code implementation.
The ```EmployeeRegion``` has a good hashing function,
and the ```BadEmployeeRegion``` has a pointedly poor hash code implementation.
The hash code is so bad that all entries in the
```BadEmployeeRegion``` end up in the same bucket.

This example is a simple demonstration of some basic Geode APIs,
as well as providing ```gfsh``` command examples.

## Steps
1. From the ```geode-examples/partitioned``` directory,
build the jar (with the ```EmployeeKey```, ```BadEmployeeKey```, 
and ```EmployeeData``` classes):

        $   ../gradlew build

1. From the ```geode-examples/partitioned``` directory,
run a script that starts a locator and two servers.
The JAR built in the first step is placed
onto the classpath when starting the servers:

        $ scripts/startAll.sh

    Each of the servers hosts both partitioned regions.
1. Run the producer to put the same 10 entries into both the
```EmployeeRegion``` and the ```BadEmployeeRegion```:

        $ ../gradlew run -Pmain=Producer
        ...
        ... 
        INFO: Inserted 10 entries in EmployeeRegion.
        INFO: Inserted 10 entries in BadEmployeeRegion.
    To see contents of the region keys, use a ```gfsh``` query:
 
        $ $GEODE_HOME/bin/gfsh
        ...
        gfsh>connect
        gfsh>query --query="select e.key from /EmployeeRegion.entries e"
    Or, to see contents of the region values, use a ```gfsh``` query:

        gfsh>query --query="select * from /EmployeeRegion"
1. Run the consumer to get and log all 10 entries in each region, 
the```EmployeeRegion``` and the ```BadEmployeeRegion```:

        $ ../gradlew run -Pmain=Consumer

    Note that the quantity of entries may also be observed with ```gfsh```:
 
        $ $GEODE_HOME/bin/gfsh
        ...
        gfsh>connect
        gfsh>describe region --name=EmployeeRegion
        ..........................................................
        Name            : EmployeeRegion
        Data Policy     : partition
        Hosting Members : server2
                          server1

        Non-Default Attributes Shared By Hosting Members  

         Type  |    Name     | Value
        ------ | ----------- | ---------
        Region | size        | 10
               | data-policy | PARTITION

        gfsh>quit

    As an alternative, ```gfsh``` maybe used to identify how many entries
    there are for each region on each server by looking at statistics.

        gfsh>show metrics --categories=partition --region=/BadEmployeeRegion --member=server1

    Within the output, the result for ```totalBucketSize``` identifies
    the number of entries hosted on the specified server.
    Vary the command to see both ```server1``` and ```server2```, as well as
    ```EmployeeRegion``` and ```BadEmployeeRegion```.

    Note that for the ```BadEmployeeRegion```, one of the servers will host
    all the entries, while the other server will not have any of the entries.
    This is due to the bad hash code generated for those keys.
1. Kill one of the servers:

        $ $GEODE_HOME/bin/gfsh
        ...
        gfsh>connect
        gfsh>stop server --name=server1
        gfsh>quit

5. Run the consumer a second time, and notice that only approximately half of
the entries of the ```EmployeeRegion``` are still available: 

        $ ../gradlew run -Pmain=Consumer
        ...
        ...
        INFO: Done. 6 entries available on the server(s).

    Again, this observation may also be made with ```gfsh```:

        $ $GEODE_HOME/bin/gfsh
        ...
        gfsh>connect
        gfsh>describe region --name=EmployeeRegion
        ..........................................................
        Name            : EmployeeRegion
        Data Policy     : partition
        Hosting Members : server2

        Non-Default Attributes Shared By Hosting Members  

         Type  |    Name     | Value
        ------ | ----------- | ---------
        Region | size        | 4
               | data-policy | PARTITION

        gfsh>quit

6. Shut down the system:

        $ scripts/stopAll.sh

