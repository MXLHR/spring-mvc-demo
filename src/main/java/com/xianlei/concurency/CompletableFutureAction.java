package com.xianlei.concurency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class CompletableFutureAction {

	@Test
	public void test1() {
		// 使用异步API
		Shop shop = new Shop("BestShop");
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getPriceAsync("productName1");
		long invocationTime = (System.nanoTime() - start) / 1_000_000;
		System.out.println("主线程等待时间：" + invocationTime);

		// 执行其他任务
		// doSomething()...

		try {
			double price = futurePrice.get();
			System.out.println("计算结果：" + price);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("主线程执行时间：" + (System.nanoTime() - start) / 1_000_000);
	}

	@Test
	public void test2() {
		// 使用异步API
		Shop shop = new Shop("BestShop");
		long start = System.nanoTime();
		List<String> result = shop.findPrices3("myPhone");
		System.out.println(result.toString());
		long invocationTime = (System.nanoTime() - start) / 1_000_000;
		System.out.println("主线程等待时间：" + invocationTime);

		// 执行其他任务
		// doSomething()...

		// try {
		// double price = futurePrice.get();
		// System.out.println("计算结果："+price);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// e.printStackTrace();
		// }
		System.out.println("主线程执行时间：" + (System.nanoTime() - start) / 1_000_000);
	}

	/**
	 * supplyAsync用于有返回值的任务，runAsync则用于没有返回值的任务
	 * allOf是等待所有任务完成，构造后CompletableFuture完成
	 * 
	 * anyOf是只要有一个任务完成，构造后CompletableFuture就完成
	 */
	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		// 结果集
		List<String> list = new ArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
		// 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
		CompletableFuture[] cfs = taskList.stream()
				.map(integer -> CompletableFuture.supplyAsync(() -> calc(integer), executorService)
						.thenApply(h -> Integer.toString(h)).whenComplete((s, e) -> {
							System.out.println("任务" + s + "完成!result=" + s + "，异常 e=" + e + "," + new Date());
							list.add(s);
						}))
				.toArray(CompletableFuture[]::new);
		// 封装后无返回值，必须自己whenComplete()获取
		CompletableFuture.allOf(cfs).join();
		System.out.println("list=" + list + ",耗时=" + (System.currentTimeMillis() - start));
	}

	public static Integer calc(Integer i) {
		try {
			if (i == 1) {
				Thread.sleep(3000);// 任务1耗时3秒
			} else if (i == 5) {
				Thread.sleep(5000);// 任务5耗时5秒
			} else {
				Thread.sleep(1000);// 其它任务耗时1秒
			}
			System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！+" + new Date());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return i;
	}

	/*
	 * 进行转换 
	 * public <U> CompletionStage<U> thenApply(Function<? super T,? extends U>
	 * fn); public <U> CompletionStage<U> thenApplyAsync(Function<? super T,?
	 * extends U> fn); public <U> CompletionStage<U> thenApplyAsync(Function<? super
	 * T,? extends U> fn,Executor executor);
	 */
	@Test
	public void thenApply() {
		String result = CompletableFuture.supplyAsync(() -> "hello") // CompletableFuture<String>
				.thenApply(s -> s + " world").join();
		System.out.println(result);
	}

	/**
	 * 进行消耗 public CompletionStage<Void> thenAccept(Consumer<? super T> action);
	 * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action);
	 * public CompletionStage<Void> thenAcceptAsync(Consumer<? super T>
	 * action,Executor executor); thenAccept是针对结果进行消耗，因为他的入参是Consumer，有入参无返回值。 例如：
	 */
	@Test
	public void thenAccept() {
		CompletableFuture.supplyAsync(() -> "hello").thenAccept(s -> System.out.println(s + " world"));
	}

	/**
	 * 对上一步的计算结果不关心，执行下一个操作。 
	 * public CompletionStage<Void> thenRun(Runnable action);
	 * public CompletionStage<Void> thenRunAsync(Runnable action); public
	 * CompletionStage<Void> thenRunAsync(Runnable action,Executor executor);
	 * thenRun它的入参是一个Runnable的实例，表示当得到上一步的结果时的操作。 例如：
	 */
	@Test
	public void thenRun() {
		CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "hello";
		}).thenRun(() -> System.out.println("hello world"));//表示当得到上一步的结果时的操作
		while (true) {
		}
	}
	/**
	4.结合两个CompletionStage的结果，进行转化后返回

	public <U,V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
	public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn);
	public <U,V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other,BiFunction<? super T,? super U,? extends V> fn,Executor executor);
	它需要原来的处理返回值，并且other代表的CompletionStage也要返回值之后，利用这两个返回值，进行转换后返回指定类型的值。
	例如：
	 */
	@Test
	public void thenCombine() {
		Boolean result = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}).thenCombine(CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return true;
		}), (s1, s2) -> s1 & s2 ).join();
		//这两个任务时并发执行的
		System.out.println(result);
	}
	
	/*结合两个CompletionStage的结果，进行消耗
	public <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
	public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action);
	public <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other,BiConsumer<? super T, ? super U> action,     Executor executor);
	它需要原来的处理返回值，并且other代表的CompletionStage也要返回值之后，利用这两个返回值，进行消耗。
	例如：*/

	@Test
	public void thenAcceptBoth() {
		CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "hello";
		}).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "world";
		}), (s1, s2) -> System. out.println(s1 + " " + s2));
		while (true) {
		}
	}

	/*在两个CompletionStage都运行完执行。
	public CompletionStage<Void> runAfterBoth(CompletionStage<?> other,Runnable action);
	public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action);
	public CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other,Runnable action,Executor executor);
	不关心这两个CompletionStage的结果，只关心这两个CompletionStage执行完毕，之后在进行操作（Runnable）。
	例如：*/

	@Test
	public void runAfterBoth() {
		CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "s1";
		}).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			return "s2"; 
		}), () -> System.out.println("hello world"));
		while (true) {
		}
	}

	
/*	6.两个CompletionStage，谁计算的快，我就用那个CompletionStage的结果进行下一步的转化操作。

	public <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other,Function<? super T, U> fn);
	public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn);
	public <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other,Function<? super T, U> fn,Executor executor);
	我们现实开发场景中，总会碰到有两种渠道完成同一个事情，所以就可以调用这个方法，找一个最快的结果进行处理。
	例如：*/

	@Test
	public void applyToEither() {
		String result = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "s1";
		}).applyToEither(CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "hello world";
		}), s -> s).join();
		System.out.println(result);
	}
	
	/*两个CompletionStage，谁计算的快，我就用那个CompletionStage的结果进行下一步的消耗操作。
	public CompletionStage<Void> acceptEither(CompletionStage<? extends T> other,Consumer<? super T> action);
	public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action);
	public CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other,Consumer<? super T> action,Executor executor);
	例如：*/

	@Test
	public void acceptEither() {
		CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "s1";
		}).acceptEither(CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "hello world";
		}), System.out::println);
		while (true) {
		}
	}

	/*
	两个CompletionStage，任何一个完成了都会执行下一步的操作（Runnable）。
	public CompletionStage<Void> runAfterEither(CompletionStage<?> other,Runnable action);
	public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action);
	public CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other,Runnable action,Executor executor);
	例如：*/

	@Test
	public void runAfterEither() {
		CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "s1";
		}).runAfterEither(CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "s2";
		}), () -> System.out.println("hello world"));
		while (true) {
		}
	}

	/*
	当运行时出现了异常，可以通过exceptionally进行补偿。
	public CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn);
	例如：*/

	@Test
	public void exceptionally() {
		String result = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (1 == 1) {
				throw new RuntimeException("测试一下异常情况");
			}
			return "s1";
		}).exceptionally(e -> {
			System.out.println(e.getMessage());
			return "hello world";
		}).join();
		System.out.println(result);
	}

	
/*	当运行完成时，对结果的记录。这里的完成时有两种情况，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断。这里为什么要说成记录，因为这几个方法都会返回CompletableFuture，当Action执行完毕后它的结果返回原始的CompletableFuture的计算结果或者返回异常。所以不会对结果产生任何的作用。
	public CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action);
	public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action);
	public CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action,Executor executor);
	例如：*/

	@Test
	public void whenComplete() {
		String result = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (1 == 1) {
				throw new RuntimeException("测试一下异常情况");
			}
			return "s1";
		}).whenComplete((s, t) -> {
			System.out.println(s);
			System.out.println(t.getMessage());
		}).exceptionally(e -> {
			System.out.println(e.getMessage());
			return "hello world";
		}).join();
		System.out.println(result);
	}


	/*
	 	结果为：null
	java.lang.RuntimeException: 测试一下异常情况
	java.lang.RuntimeException: 测试一下异常情况
	hello world*/
	
/*	这里也可以看出，如果使用了exceptionally，就会对最终的结果产生影响，它没有口子返回如果没有异常时的正确的值，这也就引出下面我们要介绍的handle。

	运行完成时，对结果的处理。这里的完成时有两种情况，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断。
	public <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn);
	public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn);
	public <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn,Executor executor);*/
//	出现异常时

	@Test
	public void handle() {
		String result = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 出现异常
			if (1 == 1) {
				throw new RuntimeException("测试一下异常情况");
			}
			return "s1";
		}).handle((s, t) -> {
			if (t != null) {
				return "hello world";
			}
			return s;
		}).join();
		System.out.println(result);
	}

	//未出现异常时

	@Test
	public void handle2() {
		String result = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "s1";
		}).handle((s, t) -> {
			if (t != null) {
				return "hello world";
			}
			return s;
		}).join();
		System.out.println(result);
	}
	
	/*
	结果为：

	s1
	上面就是CompletionStage接口中方法的使用实例，CompletableFuture同样也同样实现了Future，所以也同样可以使用get进行阻塞获取值，总的来说，CompletableFuture使用起来还是比较爽的，看起来也比较优雅一点。
	 */
	

}
