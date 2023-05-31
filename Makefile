all: run

clean:
	rm -f out/Solver.jar out/MonteCarloAlgorithm.jar

out/Solver.jar: out/parcs.jar src/Solver.java
	@javac -cp out/parcs.jar src/Solver.java
	@jar cf out/Solver.jar -C src Solver.class -C src
	@rm -f src/Solver.class

out/MonteCarloAlgorithm.jar: out/parcs.jar src/MonteCarloAlgorithm.java
	@javac -cp out/parcs.jar src/MonteCarloAlgorithm.java
	@jar cf out/MonteCarloAlgorithm.jar -C src MonteCarloAlgorithm.class -C src
	@rm -f src/MonteCarloAlgorithm.class

build: out/Solver.jar out/MonteCarloAlgorithm.jar

run: out/Solver.jar out/MonteCarloAlgorithm.jar
	@cd out && java -cp 'parcs.jar:Solver.jar' Solver $(ARGS)