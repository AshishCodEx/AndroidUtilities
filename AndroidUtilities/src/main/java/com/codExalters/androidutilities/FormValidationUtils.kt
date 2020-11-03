package  com.codExalters.androidutilities


import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import android.util.Log
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*
import java.util.regex.Pattern
import kotlin.math.ceil
import kotlin.math.roundToInt


/**
 * Created by codexalters on 6/2/18.
 */
object FormValidationUtils {

    fun isValidEmail(target: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidText(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && target.length >= 1
    }

    fun isValidPhone(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.PHONE.matcher(target)
            .matches() && target.length >= 8
    }

    fun isValidPassword(password: String?): Boolean {

        /*  Pattern pattern;
        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,15}$";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,15}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();*/


        var flag = false
        if (password != null && !password.equals("", false)
            && !password.equals(" ", false)
            && password.trim().length >= 4
            && password.trim().length <= 16
        ) {
            flag = true
        }
        return flag
    }

    fun isValidPassword(password: String, length: Int): Boolean {

        /*  Pattern pattern;
        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,15}$";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,15}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();*/


        var flag = false
        if (password != null && !password.equals("", false)
            && !password.equals(" ", false)
            && password.trim().length >= length
        ) {
            flag = true
        }
        return flag
    }

    fun isStringValid(value: String?): Boolean {
        var flag = false
        if (value != null && !value.equals("", false)
            && value.trim().isNotEmpty()
        ) {
            flag = true
        }
        return flag
    }


    fun isValidRePassword(pass1: String, pass2: String): Boolean {
        return pass1 == pass2
    }

    fun upperCaseFirstLetter(string: String): String {
        if (TextUtils.isEmpty(string)) {
            return string
        } else {
            val sb = StringBuilder(string)
            sb.setCharAt(0, Character.toUpperCase(sb.get(0)))
            return sb.toString()
        }
    }

    fun getValueWithCurrencyCode(amount: Double): String {
        return if (amount.toString().isEmpty() || amount.toString().isBlank()) {
            "$" + convertToDecimal("0.00")
        } else {
            "$" + convertToDecimal(amount.toString())

        }
    }

    fun getValueWithCurrencyCode(
        amount: Double,
        currencySymbol: String,
        conversionRate: String
    ): String {
        return if (amount.toString().isEmpty() || amount.toString().isBlank()) {
            currencySymbol.replace("%20", " ") + convertToDecimal("0.00")
        } else {
            currencySymbol.replace("%20", " ") + convertToDecimal(
                (amount.toString().toDouble() * conversionRate.toDouble()).toString()
            )

        }
    }

    fun getValueWithCurrencyCodeWithoutDecimalValue(
        amount: Double,
        currencySymbol: String,
        conversionRate: String
    ): String {


        return if (amount.toString().isEmpty() || amount.toString().isBlank()) {
            currencySymbol.replace("%20", " ") + convertToRoundWithoutDecimal(
                "0.00"
            )
        } else {
            currencySymbol.replace("%20", " ") + convertToRoundWithoutDecimal(
                (amount.toString().toDouble() * conversionRate.toDouble()).roundToInt().toString()
            )

        }
    }

    fun getValueWithCurrencyCodeWithoutDecimalValueCeiling(
        amount: Double,
        currencySymbol: String,
        conversionRate: String
    ): String {


        return if (amount.toString().isEmpty() || amount.toString().isBlank()) {
            currencySymbol.replace("%20", " ") + convertToRoundWithoutDecimal(
                "0.00"
            )
        } else {
            currencySymbol.replace("%20", " ") + convertToRoundWithoutDecimal(
                (amount.toString().toDouble() * conversionRate.toDouble()).roundToInt().toString()
            )

            /*  currencySymbol.replace("%20", " ") + convertToRoundWithoutDecimal(
                 ceil(amount.toString().toDouble() * conversionRate.toDouble()).toString()
             )*/

        }
    }


    fun getValueWithConversionRate(amount: Double, conversionRate: Double): Double {
        return ceil((amount * conversionRate))
    }


    fun convertToRoundWithoutDecimal(str: String): String {
        return try {

            val symbols = DecimalFormatSymbols(Locale.ENGLISH)

            DecimalFormat("0", symbols).format(str.toDouble())
        } catch (e: Exception) {
            Log.e("FormValidationUtils", "convertToDecimal : ${e.printStackTrace()} ")
            str
        }

    }

    fun convertToDecimal(str: String): String {
        return try {

            val symbols = DecimalFormatSymbols(Locale.ENGLISH)

            DecimalFormat("0.00", symbols).format(str.toDouble())
        } catch (e: Exception) {
            Log.e("FormValidationUtils", "convertToDecimal : ${e.printStackTrace()} ")
            str
        }

    }


    fun getLocationDecimalFormat(): DecimalFormat {

        val decimalFormat = DecimalFormat("#.########", DecimalFormatSymbols(Locale.ENGLISH))

        decimalFormat.roundingMode = RoundingMode.HALF_UP

        return decimalFormat
    }

    class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) : InputFilter {

        private var mPattern: Pattern =
            Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {

            val matcher = mPattern.matcher(dest)
            return if (!matcher.matches()) "" else null
        }

    }

    class AlphaNumericInputFilter : InputFilter {
        override fun filter(
            source: CharSequence, start: Int, end: Int,
            dest: Spanned, dstart: Int, dend: Int
        ): CharSequence? {

            // Only keep characters that are alphanumeric
            val builder = StringBuilder()
            for (i in start until end) {
                val c = source[i]
                if (Character.isLetterOrDigit(c)) {
                    builder.append(c)
                }
            }

            // If all characters are valid, return null, otherwise only return the filtered characters
            val allCharactersValid = builder.length == end - start
            return if (allCharactersValid) null else builder.toString().toUpperCase()
        }
    }


}