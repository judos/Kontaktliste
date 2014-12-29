package helpers.lib.adapter.chardet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;

import ch.judos.generic.data.ArraysJS;

/**
 * @since 29.12.2014
 * @author Julian Schelker
 */
public class CharDetector {

	private static class WrappedString {
		public String	value;

		public WrappedString(String value) {
			this.value = value;
		}

	}

	public Charset detectFile(File file) throws FileNotFoundException {
		final AtomicBoolean found = new AtomicBoolean(false);
		final WrappedString result = new WrappedString("");

		nsDetector det = new nsDetector();
		det.Init(new nsICharsetDetectionObserver() {
			@Override
			public void Notify(String charset) {
				found.set(true);
				result.value = charset;
			}
		});

		byte[] buf = new byte[1024];
		int len;
		boolean done = false;
		boolean isAscii = true;

		try (BufferedInputStream imp = new BufferedInputStream(new FileInputStream(file))) {
			while ((len = imp.read(buf, 0, buf.length)) != -1 && !found.get()) {

				// Check if the stream is only ascii.
				if (isAscii)
					isAscii = det.isAscii(buf, len);

				// DoIt if non-ascii and not done yet.
				if (!isAscii && !done)
					done = det.DoIt(buf, len, false);
			}
		} catch (IOException e1) {}
		det.DataEnd();

		if (isAscii && !found.get()) {
			result.value = "ASCII";
			found.set(true);
		}

		if (!found.get())
			l: {
				String prob[] = det.getProbableCharsets();
				String prio[] = new String[] { "windows-1252" };
				for (String priority : prio) {
					if (ArraysJS.contains(prob, priority)) {
						result.value = priority;
						break l;
					}
				}
				result.value = prob[1];
			}

		try {
			return Charset.forName(result.value);
		} catch (Exception e) {
			return StandardCharsets.ISO_8859_1;
		}
	}
}
