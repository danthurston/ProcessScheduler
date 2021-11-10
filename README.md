# Java Process Scheduling System

## Introduction
The system being studied has multiple queues that contain jobs of a specific priority. It then utilizes a Shortest-Job-First (SJF) method of iterating through the jobs in each queue. 

## System Issues
Although the Shortest-Job-First method of scheduling can be proven to be the quickest (A.S., P.B.G., G.G., c2013), when combined with priority scheduling, the main issue it brings is starvation (or long waiting times) in CPU-bound systems. This is caused by two main sources, jobs of low-priority and jobs of long-length. 

### Lower-Priority Process Starvation
A simple method of fixing the starvation of lower-priority processes is to increase the priority of each process if it’s not completed in a certain amount of time. This requires applying aging to each process. When a process passes a certain age, it’s passed to the next-highest priority queue. To execute this, every time a process is completed a method is called that loops over all remaining process and increments their age relative to the time passed (simulated as the bursts preceding it). Once a process passes a certain age threshold it is passed to the next queue. 

### Long Process Starvation 
Another issue that can become prevalent when utilizing these scheduling methods is processes requiring long CPU burst times are constantly overtaken by shorter processes within its fixed-priority queue. This means that long processes are slow to be dealt with or are never completed. 
An elegant solution to this issue is adding weight to each process. This is known as weighted-shortest-job-first and is an adaptation of agile methodologies being applied to CPU scheduling. This would ideally be done using an algorithm that uses the processes burst and age. The calculation would result in processes that are over a certain weight being moved further up their respective priority queues as they age. 

In retrospect, the limitations of java priority queues mean processes cannot simply change position in the queue. This would require removing, adding and re-looping over each process in the queue every time one’s weight passes the threshold. Instead, the weight is made equivalent to the processes burst. This means time-complexity is kept to a minimum as the adjustment is performed during the queue’s initial formation. To implement this, the custom comparator used to order processes by burst level has an additional statement that places any process with a burst over a certain threshold higher up the queue. 

## Conclusion
As the original method of shortest-job-first is commonly cited as one of the most efficient scheduling algorithms, the solutions chosen were modifications of SJF rather than simply altering to a less efficient method where issues such as dispatch latency can become an issue. This means the problems were solved whilst retaining the efficiency of the shortest job first scheduling algorithm. 
As the quantity of processes and their data can vary significantly depending on the data imported, fields for altering all thresholds is provided in the UI.


## References
Avi Silberchatz, Peter Baer Galvin and Greg Gagne - A.S., P.B.G., G.G. - c2013. University of Illinois Chicago. [Online]. [28 March 2019]. Available from: https://www2.cs.uic.edu/~jbell/CourseNotes/OperatingSystems/6_CPU_Scheduling.html 
