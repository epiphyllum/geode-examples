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

This basic example demonstrates the properties of a partitioned region. 
Two servers host a partitioned region, which has no redundancy.
The producer puts 50 entries into the partitioned region.
The consumer prints the number of entries in the region.
Due to partitioning,
the entries are distributed among the two servers hosting the region.
Since there is no redundancy of the data within the region,
when one of the servers goes away,
the entries hosted within that server are also gone.

This example is a simple demonstration of some basic Geode APIs,
as well how to write tests using mocks for Geode applications.

## Steps
1. From the ```geode-examples/partitioned``` directory,
build the jar (with the EmployeeKey and EmployeeData classes),
which is put onto the classpath when starting the servers:

        $   ../gradlew build

1. From the ```geode-examples/partitioned``` directory,
run a script that starts a locator and two servers:

        $ scripts/startAll.sh

    Each of the servers hosts the partitioned region called ```EmployeeRegion```.

2. Run the producer to put 50 entries into ```EmployeeRegion```:

        $ ../gradlew run -Pmain=Producer
        ...
        ... 
        INFO: Done. Inserted 50 entries.

    To see contents of the region keys, use a ```gfsh``` query:
 
        $ $GEODE_HOME/bin/gfsh
        ...
        gfsh>connect
        gfsh>query --query="select e.key from /EmployeeRegion.entries e"

    or, to see contents of the region values, use a ```gfsh``` query:

        gfsh>query --query="select * from /EmployeeRegion"

3. Run the consumer to observe that there are 50 entries in ```EmployeeRegion```:

        $ ../gradlew run -Pmain=Consumer
        ...
        ...
        INFO: Done. 50 entries available on the server(s).

    Note that this observation may also be made with ```gfsh```:
 
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
        Region | size        | 50
               | data-policy | PARTITION

        gfsh>quit

4. Kill one of the servers:

        $ $GEODE_HOME/bin/gfsh
        ...
        gfsh>connect
        gfsh>stop server --name=server1
        gfsh>quit

5. Run the consumer a second time, and notice that only approximately half of
the entries are still available: 

        $ ../gradlew run -Pmain=Consumer
        ...
        ...
        INFO: Done. 25 entries available on the server(s).

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
        Region | size        | 25
               | data-policy | PARTITION

        gfsh>quit

6. Shut down the system:

        $ scripts/stopAll.sh
