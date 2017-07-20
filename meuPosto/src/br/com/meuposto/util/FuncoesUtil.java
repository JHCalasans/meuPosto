package br.com.meuposto.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Months;
import org.joda.time.Weeks;



public final class FuncoesUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat sdfHMS = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	public static DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
	public static DecimalFormat df4 = new DecimalFormat("#,###,###,##0.0000");
	public static DecimalFormat df6 = new DecimalFormat("#,###,###,##0.000000");

	private static final DecimalFormat DF_NUM_OFICIO = new DecimalFormat("0000");

	public static Date removeTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static boolean datasIguais(Date data1, Date data2) {
		Date d1 = removeTime(data1);
		Date d2 = removeTime(data2);
		return d1.equals(d2);
	}

	public static boolean isSameYear(Date... dates) {
		Calendar current = Calendar.getInstance();
		Calendar before = Calendar.getInstance();
		for (int i = 0; i < dates.length; i++) {
			current.setTime(dates[i]);
			if (i > 0
					&& (current.get(Calendar.YEAR) != before.get(Calendar.YEAR)))
				return false;

			before.setTime(dates[i]);
		}
		return true;
	}

	public static boolean isWeekend(Calendar date) {
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
	}

	public static boolean isWeekend(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return isWeekend(c);
	}

	public static boolean isEspecificDay(Date date, int dayOfWeek) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return isEspecificDay(c, dayOfWeek);
	}

	public static boolean isEspecificDay(Calendar date, int dayOfWeek) {
		return date.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
	}

	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static boolean isAfterDayOfWeek(Date current, Date after) {
		Calendar cCurrent = Calendar.getInstance();
		cCurrent.setTime(current);
		Calendar cAfter = Calendar.getInstance();
		cAfter.setTime(after);
		int dayOfWeekCurr = cCurrent.get(Calendar.DAY_OF_WEEK);
		int dayOfWeekAfter = cAfter.get(Calendar.DAY_OF_WEEK);
		if (++dayOfWeekCurr == dayOfWeekAfter)
			return true;
		else
			return false;
	}

	public static boolean isPreviousDay(Date day, Date previous) {
		return compareDay(day, previous, false);
	}

	public static boolean isNextDay(Date day, Date next) {
		return compareDay(day, next, true);
	}

	private static boolean compareDay(Date day, Date otherDay, boolean isNext) {
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(day);
		Calendar cOtherDay = Calendar.getInstance();
		cOtherDay.setTime(otherDay);
		int amount = isNext ? -1 : 1;
		if (cDay.get(Calendar.DAY_OF_MONTH) == (cOtherDay
				.get(Calendar.DAY_OF_MONTH) + amount)
				&& cDay.get(Calendar.MONTH) == cOtherDay.get(Calendar.MONTH)
				&& cDay.get(Calendar.YEAR) == cDay.get(Calendar.YEAR))
			return true;
		else
			return false;
	}

	public static int obterQuantSemanasEntreDatas(Date dtInicial, Date dtFinal) {
		DateTime cDtInicial = new DateTime(dtInicial);
		DateTime cDtFinal = new DateTime(dtFinal);
		return Weeks.weeksBetween(
				cDtInicial.dayOfWeek().withMinimumValue().minusDays(1),
				cDtFinal.dayOfWeek().withMaximumValue().plusDays(1)).getWeeks();
	}

	public static int obterQuantMesesEntreDatas(Date dtInicial, Date dtFinal) {
		DateTime cDtInicial = new DateTime(dtInicial);
		DateTime cDtFinal = new DateTime(dtFinal);
		return Months.monthsBetween(cDtInicial.withDayOfMonth(1),
				cDtFinal.withDayOfMonth(1)).getMonths();
	}

	public static Date sendToNextFriday(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int amount = Calendar.FRIDAY - dayOfWeek;
		c.add(Calendar.DAY_OF_WEEK, amount);
		return c.getTime();
	}

	public static Date sendToNextSunday(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int amount = Calendar.SATURDAY - dayOfWeek;
		c.add(Calendar.DAY_OF_WEEK, ++amount);
		return c.getTime();
	}

	public static Date sendToLastSunday(Date data) {
		DateTime cDtInicial = new DateTime(data);
		return cDtInicial.withDayOfWeek(DateTimeConstants.SUNDAY).plusWeeks(-1)
				.toDate();
	}

	public static long diferencaDiasEntreDatas(Date dtMenor, Date dtMaior) {
		long dif = dtMaior.getTime() - dtMenor.getTime();

		return dif / (24 * 60 * 60 * 1000);
	}

	public static String removeAccents(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str.trim();

	}

	public static BigDecimal criarBigDecimal(double valor, int nrCasasDecimais) {
		BigDecimal tmp = new BigDecimal(valor, MathContext.UNLIMITED);
		return tmp.setScale(nrCasasDecimais, RoundingMode.HALF_UP);
	}

	public static boolean isMaior(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) == 1);
	}

	public static boolean isMaiorOuIgual(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) > -1);
	}

	public static boolean isMenor(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) == -1);
	}

	public static boolean isMenorOuIgual(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) < 1);
	}

	public static boolean isIgual(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) == 0);
	}

	public static String criptografarSenha(String senha) throws Exception{
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			String retorno = hexString.toString();
			return retorno;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao processar senha");
		}
		
	}
}
