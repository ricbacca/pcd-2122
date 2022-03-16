package pcd.ass01.seq;

import java.util.*;

public class Simulator {

	private static final double dt = 0.001;

	private final SimulationView viewer;

	/* bodies in the field */
	private ArrayList<Body> bodies;

	/* boundary of the field */
	private final Boundary bounds;

	public Simulator(SimulationView viewer, long nBodies) {
		this.viewer = viewer;
		this.bounds = new Boundary(-6.0, -6.0, 6.0, 6.0);

		initializeBodySet(nBodies, bounds);
	}
	
	public void execute(long nSteps) {
		/* init virtual time and iterations counter */
		double vt = 0;
		long iter = 0;

		/* simulation loop */
		while (iter < nSteps) {
			/* compute bodies velocity: total force and acceleration */
			bodies.forEach(b -> {
				V2d totalForce = computeTotalForceOnBody(b);
				V2d acc = new V2d(totalForce).scalarMul(1.0 / b.getMass());
				b.updateVelocity(acc, dt);
			});

			/* compute bodies new pos */
			bodies.forEach(b -> b.updatePos(dt));

			/* check collisions with boundaries */
			bodies.forEach(b -> b.checkAndSolveBoundaryCollision(bounds));

			/* update virtual time */
			vt += dt;
			iter++;

			/* display current stage */
			viewer.display(bodies, vt, iter, bounds);
		}
	}

	private V2d computeTotalForceOnBody(Body b) {
		V2d totalForce = new V2d(0, 0);

		/* compute total repulsive force, from all other
		bodies to the actually selected body */
		bodies.forEach(otherBody -> {
			if (!b.equals(otherBody)) {
				V2d forceByOtherBody = null;
				try {
					forceByOtherBody = b.computeRepulsiveForceBy(otherBody);
				} catch (InfiniteForceException e) {
					e.printStackTrace();
				}
				totalForce.sum(forceByOtherBody != null ? forceByOtherBody : new V2d(0,0));
			}
		});

		return totalForce.sum(b.getCurrentFrictionForce());
	}

	private void initializeBodySet(final long nBodies, final Boundary bounds) {
		Random rand = new Random(System.currentTimeMillis());
		bodies = new ArrayList<>();
		for (int i = 0; i < nBodies; i++) {
			double x = bounds.getX0()*0.25 + rand.nextDouble() * (bounds.getX1() - bounds.getX0()) * 0.25;
			double y = bounds.getY0()*0.25 + rand.nextDouble() * (bounds.getY1() - bounds.getY0()) * 0.25;
			Body b = new Body(i, new P2d(x, y), new V2d(0, 0), 10);
			bodies.add(b);
		}
	}
}
