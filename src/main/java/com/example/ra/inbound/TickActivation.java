package com.example.ra.inbound;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import javax.resource.spi.BootstrapContext;
import javax.resource.spi.UnavailableException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class TickActivation extends TimerTask implements XAResource {

	private final BootstrapContext ctx;
	private final TickActivationSpec spec;

	private Timer timer;
	private AtomicInteger value;
	private TickListener listener;

	public TickActivation(BootstrapContext ctx, TickActivationSpec spec) {
		this.ctx = ctx;
		this.spec = spec;
	}

	public void start(TickListener listener) throws UnavailableException {
		this.value = new AtomicInteger();
		this.listener = listener;
		this.timer = ctx.createTimer();
		this.timer.scheduleAtFixedRate(this, 100, spec.getInterval());
	}

	@Override
	public void run() {
		listener.onTick(value.incrementAndGet());
	}

	public void stop() {
		timer.cancel();
	}

	@Override
	public void commit(Xid xid, boolean onePhase) throws XAException {
	}

	@Override
	public void end(Xid xid, int flags) throws XAException {
	}

	@Override
	public void forget(Xid xid) throws XAException {
	}

	@Override
	public int getTransactionTimeout() throws XAException {
		return 0;
	}

	@Override
	public boolean isSameRM(XAResource xares) throws XAException {
		return false;
	}

	@Override
	public int prepare(Xid xid) throws XAException {
		return 0;
	}

	@Override
	public Xid[] recover(int flag) throws XAException {
		return null;
	}

	@Override
	public void rollback(Xid xid) throws XAException {
	}

	@Override
	public boolean setTransactionTimeout(int seconds) throws XAException {
		return false;
	}

	@Override
	public void start(Xid xid, int flags) throws XAException {
	}
}
