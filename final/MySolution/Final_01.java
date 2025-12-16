/*
// HX: 20 points for Final_01
// A word consists of a sequence of
// letters ([a-z]+[A-Z]) plus aprostrophe (')
// And words are separated by non-letters-aprostrophe
// (such as blanks, punctuations, etc.) in pg2701.txt.
*/

import MyFinalLib.FnList.*;
import MyFinalLib.LnStrm.*;

public class Final_01 {
    static LnStrm<FnList<Character>> pg2701_word$strmize() {
	// HX-2025-12-16:
	// Please construct a stream of words contained in the
	// file Data/pg2701.txt
	// Note that a word is represented as a list of characters
	// Also every upper case letter in the original text should
	// be turned into its corresponding lower case.
	// This stream should be built on top of pg2701_char$strmize
	// which is already implemented in Final_00.
	// In particular, you should NOT use Java library function
	// for processing files!
	LnStrm<Character> charStream = Final_00.pg2701_char$strmize();
	return word$strmize$helper(charStream);
    }

    private static LnStrm<FnList<Character>>
	word$strmize$helper(LnStrm<Character> cs) {
	return new LnStrm<FnList<Character>>(
	    () -> {
		LnStrm<Character> cs1 = skip$nonword(cs);
		LnStcn<Character> cxs = cs1.eval0();

		if (cxs.nilq()) {
		    return new LnStcn<FnList<Character>>();
		} else {
		    WordResult result = collect$word$acc(cxs.tail, new FnList<Character>(to$lower(cxs.head), new FnList<Character>()));
		    return new LnStcn<FnList<Character>>(
			result.word,
			word$strmize$helper(result.rest)
		    );
		}
	    }
	);
    }

    private static class WordResult {
	FnList<Character> word;
	LnStrm<Character> rest;
	WordResult(FnList<Character> w, LnStrm<Character> r) {
	    word = w; rest = r;
	}
    }

    private static LnStrm<Character> skip$nonword(LnStrm<Character> cs) {
	LnStcn<Character> cxs = cs.eval0();
	if (cxs.nilq()) {
	    return new LnStrm<Character>(() -> new LnStcn<Character>());
	}
	char ch = cxs.head;
	if (is$word$char(ch)) {
	    return new LnStrm<Character>(() -> new LnStcn<Character>(ch, cxs.tail));
	} else {
	    return skip$nonword(cxs.tail);
	}
    }

    private static WordResult collect$word(LnStrm<Character> cs) {
	return collect$word$acc(cs, new FnList<Character>());
    }

    private static WordResult collect$word$acc(LnStrm<Character> cs, FnList<Character> acc) {
	LnStcn<Character> cxs = cs.eval0();
	if (cxs.nilq()) {
	    return new WordResult(acc.reverse(), new LnStrm<Character>(() -> new LnStcn<Character>()));
	}
	char ch = cxs.head;
	if (is$word$char(ch)) {
	    return collect$word$acc(cxs.tail, new FnList<Character>(to$lower(ch), acc));
	} else {
	    return new WordResult(acc.reverse(), cxs.tail);
	}
    }

    private static boolean is$word$char(char c) {
	return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '\'');
    }

    private static char to$lower(char c) {
	if (c >= 'A' && c <= 'Z') {
	    return (char)(c + ('a' - 'A'));
	}
	return c;
    }

    public static void main (String[] args) {
	// HX-2025-12-16:
	// Please write minimal testing code for pg2701_word$strmize()
	LnStrm<FnList<Character>> wordStream = pg2701_word$strmize();
	int count = 0;
	int maxWords = 20;

	while (count < maxWords) {
	    LnStcn<FnList<Character>> wstcn = wordStream.eval0();
	    if (wstcn.nilq()) {
		break;
	    }

	    FnList<Character> word = wstcn.head;
	    word.foritm(ch -> System.out.print(ch));
	    System.out.println();

	    wordStream = wstcn.tail;
	    count++;
	}

	return /*void*/;
    }
}
