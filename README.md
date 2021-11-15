# Java Process Scheduling System
###### *Object-Oriented* - *CPU Scheduling* - *Operating System Programming* - *Java*

## Introduction
This project is an attempt at improving the prioritization of scheduled processes within a system.  It currently utilizes a Shortest-Job-First (SJF) approach, iterating through multiple queues that contain jobs of specific priorities. The goal is to improve system performance with execution of tasks of varying burst time and to avoid CPU *starvation* or *long waiting times*.

## System Issues
Although the Shortest-Job-First method of scheduling is often shown to be the quickest (A.S., P.B.G., G.G., 2013), when combined with priority scheduling, the main issue it brings is starvation (or long waiting times) in CPU-bound systems. This is caused by two main sources, jobs of __low-priority__ and jobs of __long-length__. 

### Lower-Priority Process Starvation
A simple method of fixing the starvation of lower-priority processes is to increase the priority of each process if not completed in a certain amount of time. This requires applying __aging__ to each process. This entails processes being passed to the next highest priority queue when reaching a certain age. To implement this, each time a process is completed a method is called to loop over all remaining process and increment their age relative to the time passed (simulated as the bursts preceding it). Once a process passes a certain age threshold it is passed to the next queue. 

### Long Process Starvation 
Another prevalent issue when utilizing these scheduling methods is that processes requiring long CPU burst times are constantly overtaken by shorter processes within its fixed-priority queue. This means long processes are slow to be dealt with or are never completed. 
An elegant solution to this issue is adding _weight_ to each process. This is known as **weighted-shortest-job-first** and is an adaptation of agile methodologies being applied to CPU scheduling. This would ideally be implemented using an algorithm that uses the processes burst and age. The calculation would result in processes that are over a certain weight being moved further up their respective priority queues as they age. 

However, the limitations of java priority queues mean processes cannot simply change position within the queue. This would require removing, adding, and re-looping over each process in the queue every time any one processes weight passes the threshold. Instead, the weight is made equivalent to the processes burst. This keeps time-complexity to a minimum as adjustments are made during the queue’s initial formation. To implement this, the custom comparator used to order processes by burst level has an additional statement that places any process with a burst over a certain threshold higher in the queue. 

## Conclusion
As the original method of shortest-job-first is commonly cited as one of the most efficient scheduling algorithms, the solution chosen uses modifications of SJF instead of altering to a less efficient method where issues such as dispatch latency can become an issue. This meant the problems could be solved whilst retaining the efficiency of the SJF scheduling algorithm. As the quantity of processes and their data can vary significantly depending on the data imported, fields for altering all thresholds are provided in the UI.


## References
Silberchatz, A., Galvin, P.B., and Gagne, G. - 2013. CPU Scheduling. University of Illinois Chicago. [ONLINE]. Available from: https://www2.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/6_CPU_Scheduling.html. [28 March 2019]
