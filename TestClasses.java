
public class TestClasses {
	
	public static void main (String[] args) {

		City c1 = new City("Mississauga", 100, 246, 252);
		City c2 = new ProvCapital("Toronto", 300, 250, 250, "ON");
		City c3 = new City("London", 200, 450, 250);
		ProvCapital pc1 = new ProvCapital("Victoria", 100, 450, 350, "BC");
		NatCapital nc1 = new NatCapital("Halifax", 200, 550, 450);
		Program prog = new Program(false);
		
		
		// --------------- Test 1 --------------- [inheritance]
		
		boolean test1Success = false;
		

		
		if (pc1 instanceof ProvCapital && pc1 instanceof City && nc1 instanceof NatCapital && nc1 instanceof City) {
			test1Success = true;
		}
		
		if (test1Success) {
			System.out.println("Test 1 passed");
		} else {
			System.out.println("Test 1 failed");
		}
		
		
		// --------------- Test 2 --------------- [equals]
		
		boolean test2Success = false;
		
		
		
		if (c1.equals(c2) && !c1.equals(c3) && !c2.equals(c3)) {
			test2Success = true;
		}
		
		if (test2Success) {
			System.out.println("Test 2 passed");
		} else {
			System.out.println("Test 2 failed");
		}
		
		// --------------- Test 3 --------------- [toString]
		
		boolean test3Success = false;
		
		if (c1.toString().equals("Mississauga") && pc1.toString().equals("Victoria (BC)") && nc1.toString().equals("Halifax (Canada's capital)")) {
			test3Success = true;
		}
		
		if (test3Success) {
			System.out.println("Test 3 passed");
		} else {
			System.out.println("Test 3 failed");
		}
		
		
		// --------------- Test 4 --------------- [set/get name population]
		
		boolean test4Success = false;
		
		boolean tmp4 = c1.getName().equals("Mississauga") && c1.getPopulation() == 100;
		
		c1.setName("GTA");
		c1.setPopulation(700);
		
		if (tmp4 && c1.getName().equals("GTA") && c1.getPopulation() == 700) {
			test4Success = true;
		}
		
		if (test4Success) {
			System.out.println("Test 4 passed");
		} else {
			System.out.println("Test 4 failed");
		}

		// --------------- Test 5 --------------- [marker icons]
		
		boolean test5Success = false;
		
		if (c1.getMarker().toString().equals("marker_city.png") && 
				pc1.getMarker().toString().equals("marker_prov.png") &&
				nc1.getMarker().toString().equals("marker_nat.png")) {
			test5Success = true;
		}

		if (test5Success) {
			System.out.println("Test 5 passed");
		} else {
			System.out.println("Test 5 failed");
		}
		
		
		// --------------- Test 6 --------------- [expandCapacity]
		
		boolean test6Success = false;
		
		if (prog.getCityList().length == 20) {
			test6Success = true;
		}
		
		if (test6Success) {
			System.out.println("Test 6 passed");
		} else {
			System.out.println("Test 6 failed");
		}
		
		// --------------- Test 7 --------------- [rectangle selection]
		
		boolean test7Success = false;
		
		
		City[] res1 = Program.findCitiesInRect(300, 650, 800, 800);

		int numNull7 = countNullElements(res1);

		if (numNull7 == res1.length - 6) {

			if (res1[0].getName().equals("Calgary") && res1[5].getName().equals("Winnipeg")) {
				test7Success = true;
			}
		}
		
		
		
		if (test7Success) {
			System.out.println("Test 7 passed");
		} else {
			System.out.println("Test 7 failed");
		}
		
		
		// --------------- Test 8 --------------- [rectangle selection]
		
		boolean test8Success = false;
		
		City[] res2 = Program.findCitiesInRect(1050, 750, 850, 650);

		int numNull8 = countNullElements(res2);

		if (numNull8 == res2.length - 4) {

			if (res2[0].getName().equals("Charlottetown") && res2[3].getName().equals("St. John's")) {
				test8Success = true;
			}
		}
		
		
		if (test8Success) {
			System.out.println("Test 8 passed");
		} else {
			System.out.println("Test 8 failed");
		}
		
		// --------------- Test 9 --------------- [rectangle selection]
		
		boolean test9Success = false;
		
		
		City[] res3 = Program.findCitiesInRect(400, 200, 800, 400);

		int numNull9 = countNullElements(res3);

		if (numNull9 == res3.length) {

			test9Success = true;
		}
		
		
		if (test9Success) {
			System.out.println("Test 9 passed");
		} else {
			System.out.println("Test 9 failed");
		}
		
		
		// --------------- Test 10 --------------- [population stats]
		
		boolean test10Success = false;
		
		Object[] stats = Program.defaultTextboxInfo();
		Object[] actStats = new Object[] {520357.142, 8000, "Iqaluit (Nunavut)", 3000000, "Toronto (Ontario)",
				1050000.0, 405000, "London", 1780000, "Montreal"};

		if (correctResults(stats, actStats)) {
			test10Success = true;
		}
		
		if (test10Success) {
			System.out.println("Test 10 passed");
		} else {
			System.out.println("Test 10 failed");
		}
		
	}
	
	private static int countNullElements (City[] arr) {
		int num = 0;
		for (int i = arr.length - 1; i >= 0; i--) {
			if (arr[i] == null) {
				num++;
			}
		}
		
		return num;
	}
	
	private static boolean correctResults (Object[] A, Object[] B) {
		if (A.length != B.length) return false;
		
		int num = 0;
		
		for (int i = 0; i < A.length; i++) {
			
			if (A[i] instanceof City) {

				if (A[i].toString().equals(B[i])) {
					num++;
				}
			} else if (A[i] instanceof Integer) {
				if (A[i].equals(B[i])) {
					num++;
				}
			
			} else if (A[i] instanceof Double) {
				if (Math.abs((double)(A[i]) - (double)(B[i])) < 0.05) {
					num++;
				}
			}
		}
		
		if (num == A.length) return true;
		else return false;
	}

}
