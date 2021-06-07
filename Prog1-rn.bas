010 gosub 50 
020 if aux < 100 goto 43 
030 input nome 
040 for i = 1 to 20 
050 print i 
060 next i 
070 if max > mult then goto 14 else goto 10 
080 gosub 50 
090 end 
100 print aux 
110 let aux = aux + 1 
120 return 
