avac -cp ".:choco-solver.jar:" *.java
java -cp "choco-solver.jar:.:" Main ../data/a_an_example.in.txt
java -cp "choco-solver.jar:.:" Main ../data/b_better_start_small.in.txt
java -cp "choco-solver.jar:.:" Main ../data/c_collaboration.in.txt
java -cp "choco-solver.jar:.:" Main ../data/d_dense_schedule.in.txt
java -cp "choco-solver.jar:.:" Main ../data/e_exceptional_skills.in.txt
