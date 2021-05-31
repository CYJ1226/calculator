package kr.dja.BackwardOper.Stack;

public final class Stack<T>
{
	private final int slotSize;
	private final int fullSize;// -1�ϰ�� ���� ����.
	private StackLinkSlot<T> lastSlot;
	
	public Stack() { this(10); }
	public Stack(int slotSize) { this(slotSize, -1); }
	public Stack(int slotSize, int fullSize)
	{// ���� ������� ���� Ǯ ����� ����.
		this.slotSize = slotSize;
		this.fullSize = fullSize;
		this.lastSlot = new StackLinkSlot<T>(slotSize, null);
	}
	public void pushBack(T member)
	{// Ǫ��.
		if(this.fullSize != -1 && this.size() > this.fullSize)
		{// ���� Ǯ�ϰ��.
			throw new StackOverflowError();
		}
		if(this.lastSlot.isFull())
		{// ������ ������ ���� á�����.
			this.lastSlot = this.lastSlot.createBackSlot();
		}
		this.lastSlot.push(member);
	}
	public T getBack()
	{// ������ ���� �� ���.
		return this.lastSlot.getBack();
	}
	public void popBack()
	{// ��.
		if(this.lastSlot.getFrontSlot() == null && this.lastSlot.isEmpty())
		{
			throw new StackOverflowError();
		}
		this.lastSlot.popBack();
		if(this.lastSlot.getFrontSlot() != null && this.lastSlot.isEmpty())
		{// ������ ������ �������� ������.
			this.lastSlot = this.lastSlot.getFrontSlot();
			this.lastSlot.deleteBackSlot();
		}
	}
	public T getMemberAt(int index)
	{// �ε����� ������. (5�� 2�� ���� ����)
		StackLinkSlot<T> taskSlot = this.lastSlot;
		if (this.size() - index <= 0 || index < 0)
		{// �ε��� �ʰ� Ȥ�� �̸�.
			throw new IndexOutOfBoundsException();
		}
		int deeps = (this.size() - 1) / this.slotSize;// ��ü ������ �����Դϴ�.
		int floorDownCount = (deeps + 1) * this.slotSize - 1 - index;// top ���� ������ ī��Ʈ �����Դϴ�.
		int inSlotIndex = (this.slotSize - (floorDownCount % this.slotSize) - 1);// ã�� ���Կ����� �ش� ��� ��ġ�Դϴ�.
		for(int i = floorDownCount / this.slotSize ; i > 0; --i)
		{// i�� ã�� ������ ���� ���� �Դϴ�.
			taskSlot = taskSlot.getFrontSlot();// �ε����� �ش��ϴ� �����͸� ������ �ִ� ������ ���� ������ �������ϴ�.
		}
		return taskSlot.getMember(inSlotIndex);// ��� ã�Ƽ� ����.
	}
	public int size()
	{// ������ ũ�� ����.
		int count = -1;
		StackLinkSlot<T> taskSlot = this.lastSlot;
		do
		{
			taskSlot = taskSlot.getFrontSlot();
			++count;
		}
		while (taskSlot != null);
		return count * this.slotSize + this.lastSlot.getSize();
	}
	public int getFullSize()
	{// ���� Ǯ ������ ���.
		return this.fullSize;
	}
	public int getSlotSize()
	{// ���� ������ ���.
		return this.slotSize;
	}
	@Override public String toString()
	{
		String returnString = "";
		for(int i = 0; i < this.size(); i++)
		{
			returnString += this.getMemberAt(i).toString() + " ";
		}
		return returnString;
	}
}
		/*
		* ������ ����.
		* ��ũ�� ����Ʈ ���.
		* �����ʹ� �迭�� ����.
		*/
final class StackLinkSlot<T>
{
	private Object[] members;// ������ �����.
	private final int memberArrSize;
	private int stackHeight = 0;
	private StackLinkSlot<T> frontSlot = null, backSlot = null;
	
	public StackLinkSlot(int size, StackLinkSlot<T> frontSlot)
	{// ������, �ٷ� �� ���� �ޱ�.
		this.members = new Object[size];
		this.memberArrSize = size;
		this.frontSlot = frontSlot;
	}
	public StackLinkSlot<T> getFrontSlot()
	{// �ٷ� �� ���� ����.
		return this.frontSlot;
	}
	public StackLinkSlot<T> createBackSlot()
	{// �� ���� ����.
		this.backSlot = new StackLinkSlot<T>(this.memberArrSize, this);
		return this.backSlot;
	}
	public void deleteBackSlot()
	{// �� ���� ����.
		this.backSlot = null;
	}
	public boolean isFull()
	{// �� á���� true.
		return stackHeight >= memberArrSize;
	}
	public boolean isEmpty()
	{// ������� true.
		return this.stackHeight <= 0;
	}
	public int getSize()
	{// ���Կ� ���� �� �ִ� ������ ����.
		return this.stackHeight;
	}
	public void push(T member)
	{// ���Կ� ������ Ǫ��.
		if(member == null) System.out.println("ERR NULL");
		this.members[this.stackHeight] = member;
		++this.stackHeight;
	}
	@SuppressWarnings("unchecked") public T getBack()
	{// ������ ���� �� ������ ���.
		return (T)this.members[this.stackHeight - 1];
	}
	@SuppressWarnings("unchecked") public T getMember(int index)
	{// ���Կ��� �ε����� ��� ���.
		return (T)this.members[index];
	}
	public void popBack()
	{// ��.
		--this.stackHeight;
	}
}