package com.xianlei.base;

class Generic<T extends Number> {
	
}

//泛型类UpBoundGeneric  类型参数上边界Number
public class UpBoundGeneric<T extends Number> {
	
	private T data;

	public UpBoundGeneric(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public void showDataType() {
		System.out.println("数据类型是："+data.getClass().getName());
	}
}

class UpBoundGenericDomo {
	public static void myMethod(UpBoundGeneric<? extends Number> g) {
		g.showDataType();
	}
}
