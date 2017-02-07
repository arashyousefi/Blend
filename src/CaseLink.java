
public class CaseLink implements Comparable<CaseLink> {
	public Integer address, value;
	public CaseLink next;

	public CaseLink(int address, int value) {
		this.address = address;
		this.value = value;
	}

	@Override
	public int compareTo(CaseLink o) {
		return ((Integer) this.value).compareTo((Integer) o.value);
	}

}
