# -Auto-Tracking-with-Spark-and-Redis

Dataset: https://www.microsoft.com/en-us/research/publication/t-drive-trajectory-data-sample/

Dataset
==============
The dataset for this project is freely available from the Microsoft website for research:
https://www.microsoft.com/en-us/research/publication/t-drive-trajectory-data-sample/

However, we have processed the data set to suit our need to simulate time-oriented data from different sources so you can freely download our consolidated version of the data here:
https://drive.google.com/open?id=0B0MdkEsxQHAQYjFPTTVRTmZqTWc 

In this spark project, we will embark on real-time data collection and aggregation from a simulated real-time system.

The dataset for the project which will simulate our sensor data delivery is from Microsoft Research Asia GeoLife project. According to the paper, the dataset recoded a broad range of users’ outdoor movements, including not only life routines like go home and go to work but also some entertainments and sports activities, such as shopping, sightseeing, dining, hiking, and cycling. This trajectory dataset can be used in many research fields, such as mobility pattern mining, user activity recognition, location-based social networks, location privacy, and location recommendation.

As a part of this big data project, we will use the data to provide real time aggregates of the movements along a number of dimension like effective distance, duration, trajectories and more. All streamed data will be stored in the NoSQL database - HBase.
