public final class StworzObiektZeStringa {
	
	protected static Object New (Class<?> klasa){//Ta funkcja wywo�uje konstrukcje obiektu o typie przekazanym w parametrze.
		try {
			return	klasa.newInstance();
		} catch (Exception e) {
			return null;
		}
    }
}

