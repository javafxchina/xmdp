package net.javafxchina.xmdp.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 * 文件操作
 * 
 * @author Victor
 *
 */
public class FileUtil {
	/**
	 * 将文件搬到指定路径下并改名,目的和源文件处于同一个磁盘下有更高的性能。
	 * 
	 * @param source   原文件的完整路径
	 * @param destPath 目的目录
	 * @param destName 新名称
	 */
	public static void moveFile(File sourceFile, String destPath, String destName) throws Exception {

		File dir = new File(destPath);
		createDir(dir);
		if (!sourceFile.renameTo(new File(dir, destName))) {
			throw new Exception("移动文件失败！FileName=" + sourceFile.getAbsolutePath() + ";DestPath=" + destPath
					+ ";DestName=" + destName);
		}
	}

	/**
	 * 创建指定路径的文件夹，如果存在则直接返回，不存在则创建之
	 * 
	 * @param dir 文件夹路径
	 * @throws Exception 如果创建失败并多次尝试后会抛出异常
	 */
	public static void createDir(File dir) throws Exception {
		int count = 0;
		int MaxCount = 3;
		while (count < MaxCount) {
			try {
				if (dir.exists() && dir.isDirectory()) {
					return;
				} else {
					if (!dir.mkdirs()) {
						count++;
						throw new Exception("移动文件失败:创建目录" + dir.getAbsolutePath() + "失败！");
					} else {
						return;
					}
				}
			} catch (Exception e) {
				if (count >= MaxCount) {
					throw e;
				} else {
					Thread.sleep(count * 200);
				}

			}
		}
	}

	/**
	 * 拷贝文件
	 * 
	 * @param localName  本地文件
	 * @param targetName 目标文件
	 * @throws Exception
	 */
	public static void copyLocalFile(String localName, String targetName) throws Exception {
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int byteread = 0;
			File oldfile = new File(localName);
			if (oldfile.exists()) { // 文件存在时
				File newFolder = new File(targetName);
				// 判断文件夹是否存在
				if (!newFolder.getParentFile().exists()) {
					newFolder.getParentFile().mkdirs();
					newFolder.createNewFile();
				}

				int times = 0;
				while (!oldfile.canRead()) {
					Thread.sleep(1000);
					times++;
					if (times >= 3) {
						break;
					}
				}
				inStream = new FileInputStream(oldfile); // 读入原文件
				fs = new FileOutputStream(targetName);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}

			} else {
				throw new Exception("源文件不存在:" + "localName");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			inStream.close();
			fs.flush();
			fs.close();
		}
	}

	/**
	 * 使用NIO复制文件
	 * 
	 * @param localName  本地文件
	 * @param targetName 目标文件
	 * @throws Exception
	 */
	public static void copyLocalFileUsingFileChannels(String localName, String targetName) throws Exception {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		try {
			File source = new File(localName);
			File dest = new File(targetName);
			if (source.exists()) { // 文件存在时
				File newFolder = new File(targetName);
				// 判断文件夹是否存在
				if (!newFolder.getParentFile().exists()) {
					newFolder.getParentFile().mkdirs();
				}
				inStream = new FileInputStream(source);
				inputChannel = inStream.getChannel();

				outStream = new FileOutputStream(dest);
				outputChannel = outStream.getChannel();
				outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			} else {
				throw new Exception("源文件不存在:" + "localName");
			}

		} finally {
			inputChannel.close();
			outputChannel.close();
			inStream.close();
			outStream.close();
		}
	}

	/**
	 * 获取指定文件夹的子文件夹，文件会被忽略
	 * 
	 * @param rootPath 待获取子文件夹的根文件夹的全路径
	 * @return 子文件夹列表
	 */
	public static List<File> getSubDirs(String rootPath) {
		File file = new File(rootPath);
		return getSubDirs(file);
	}

	/**
	 * 获取指定文件夹的子文件夹，文件会被忽略
	 * 
	 * @param dir 待获取子文件夹的根文件夹
	 * @return 子文件夹的列表
	 */
	public static List<File> getSubDirs(File dir) {
		List<File> result = new ArrayList<File>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				result.add(file);
			}
		}
		return result;
	}

	/**
	 * 获取dir下的指定扩展名的文件清单（不包括文件夹）
	 * 
	 * @param dir
	 * @param extNames 如果此参数未空，则列出所有子文件清单
	 * @return 符合extNames约束要求的子文件清单
	 */
	public static List<File> getSubFiles(File dir, String... extNames) {
		List<File> result = new ArrayList<File>();
		File[] files = dir.listFiles();
		for (File file : files) {
			if (!file.isDirectory()) {
				boolean isResult = false;
				if (extNames == null || extNames.length == 0) {
					isResult = true;
				} else {
					for (String ext : extNames) {
						String name = file.getName();
						if (name.toLowerCase().endsWith(ext.toLowerCase())) {
							isResult = true;
							break;
						}
					}
				}
				if (isResult) {
					result.add(file);
				}
			}
		}
		return result;
	}

	/**
	 * 获取所有的子文件和子文件夹
	 * 
	 * @param root 待获取内容的根文件夹
	 * @return 子文件和子文件夹列表
	 */
	public static List<File> getSubs(File root) {
		List<File> result = new ArrayList<File>();
		File[] files = root.listFiles();
		for (File file : files) {
			result.add(file);
		}
		return result;
	}

	/**
	 * 获取子文件个数（包括文件夹和文件） 注意：需要JDK7以上
	 * 
	 * @param rootDir 待统计的根文件夹
	 * @return 子文件个数
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static int getSubFilesCount(File rootDir) {
		Path path = rootDir.toPath();
		DirectoryStream<Path> directory = null;

		int count = 0;
		try {
			directory = Files.newDirectoryStream(path);
			for (Path p : directory) {
				// System.out.println(p);
				count++;
			}
		} catch (Exception e) {
			throw new RuntimeException("统计子文件数量时异常", e);
		} finally {
			try {
				directory.close();
			} catch (IOException e) {
				throw new RuntimeException("统计子文件数量时关闭DirectoryStream异常", e);
			}
		}
		return count;
	}

	/**
	 * 文件夹是否为空<br>
	 * <b>注意：JDK7新增API，在JDK6中必须使用File.list()方法替代，但它会有性能问题</b>
	 * 
	 * @param dir 待检查的文件夹
	 * @return 该文件夹是否为空
	 * @throws Exception 如果dir不是文件夹，或者遇到io问题则抛出异常
	 */
	public static boolean isDirEmpty(File dir) {
		Path directory = dir.toPath();
		DirectoryStream<Path> dirStream = null;
		try {
			dirStream = Files.newDirectoryStream(directory);

			return !dirStream.iterator().hasNext();
		} catch (IOException e) {
			throw new RuntimeException("判断文件是否为空时异常", e);
		} finally {
			try {
				dirStream.close();
			} catch (IOException e) {
				throw new RuntimeException("判断文件是否为空时异常", e);
			}
		}
	}

	/**
	 * 获取指定文件的扩展名
	 * 
	 * @param file 带获取扩展名的文件
	 * @return 扩展名
	 */
	public static String getExtFileName(File file) {
		String path = file.getName();
		int indexOfDot = path.lastIndexOf(".");
		if (indexOfDot == -1) {
			return "";
		}
		String extName = path.substring(indexOfDot + 1);
		return extName;
	}

	/**
	 * 获取指定文件的扩展名
	 * 
	 * @param path 带获取扩展名的文件的名称
	 * @return 扩展名
	 */
	public static String getExtFileName(String path) {
		int indexOfSep = path.lastIndexOf(File.separatorChar);
		int indexOfDot = path.lastIndexOf(".");
		if (indexOfDot == -1) {
			return "";
		}
		if (indexOfDot <= indexOfSep) {
			return "";
		}
		String extName = path.substring(indexOfDot + 1);
		return extName;
	}

	public static String getMainFileName(File file) {
		int lastdot = file.getName().lastIndexOf(".");
		if (lastdot == -1) {
			return file.getName();
		}
		return file.getName().substring(0, lastdot);
	}

	public static String getMainFileName(String path) {
		int indexOfSep = path.lastIndexOf(File.separatorChar);
		int indexOfDot = path.lastIndexOf(".");
		if (indexOfDot == -1) {
			indexOfDot = path.length();
		}
		if (indexOfDot <= indexOfSep) {
			indexOfDot = path.length();
		}
		String name = path.substring(indexOfSep + 1, indexOfDot);
		return name;
	}

	/**
	 * 删除文件并备份（实际是重命名文件），备份文件为原文件名加上后缀，后缀为参数backupSuffix
	 * 
	 * @param file         待删除并备份的文件
	 * @param backupSuffix 备份文件的后缀名
	 * @param backTimes    最多保留的备份文件份数，如果为-1则无限保留，如果为0则为物理删除，不做备份
	 */
	public static void delFileAndBackup(File file, String backupSuffix, int backTimes) {
		if (file.exists() == false) {
			return;
		}
		if (backTimes == 0) {
			if (!file.delete()) {
				throw new RuntimeException("物理删除文件失败，fileName=" + file.getAbsolutePath());
			}
		} else if (backTimes < 0) {
			String backupFileName = file.getAbsolutePath() + backupSuffix;
			File backupFile = new File(backupFileName);
			if (backupFile.exists()) {
				delFileAndBackup(backupFile, backupSuffix, -1);
			}
			if (!file.renameTo(backupFile)) {
				try {
					// TODO 休息500毫秒再试一次
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				if (!file.renameTo(backupFile)) {
					throw new RuntimeException("重命名文件失败，backupFile=" + backupFileName);
				}
			}
		} else {
			String backupFileName = file.getAbsolutePath() + backupSuffix;
			File backupFile = new File(backupFileName);
			if (backupFile.exists()) {
				delFileAndBackup(backupFile, backupSuffix, backTimes - 1);
			}
			if (!file.renameTo(backupFile)) {
				throw new RuntimeException("重命名文件失败，backupFile=" + backupFileName);
			}
		}

	}

	/**
	 * 删除文件夹并备份（实际是重命名），备份文件夹为原文件名加上后缀，后缀为参数backupSuffix
	 * 
	 * @param file         待删除并备份的文件夹
	 * @param backupSuffix 备份文件的后缀名
	 * @param backTimes    最多保留的备份文件份数，如果为-1则无限保留，如果为0则为物理删除，不做备份
	 */
	public static void delDirAndBackup(File file, String backupSuffix, int backTimes) {
		if (file.exists() == false) {
			return;
		}
		if (backTimes == 0) {
			try {
				if (!deleteIfExists(file.toPath())) {
					throw new RuntimeException("物理删除失败，dirName=" + file.getAbsolutePath());
				}
			} catch (IOException e) {
				throw new RuntimeException("物理删除失败，dirName=" + file.getAbsolutePath(), e);
			}
		} else if (backTimes < 0) {
			String backupFileName = file.getAbsolutePath() + backupSuffix;
			File backupFile = new File(backupFileName);
			if (backupFile.exists()) {
				delDirAndBackup(backupFile, backupSuffix, -1);
			}
			if (!file.renameTo(backupFile)) {
				throw new RuntimeException("重命名失败，backupFile=" + backupFileName);
			}
		} else {
			String backupFileName = file.getAbsolutePath() + backupSuffix;
			File backupFile = new File(backupFileName);
			if (backupFile.exists()) {
				delDirAndBackup(backupFile, backupSuffix, backTimes - 1);
			}
			if (!file.renameTo(backupFile)) {
				throw new RuntimeException("重命名失败，backupFile=" + backupFileName);
			}
		}

	}

	/**
	 * 复制文件夹
	 * 
	 * @param localName  待复制的文件夹
	 * @param targetName 复制的目的文件夹
	 */
	public static void copyDir(String localName, String targetName) {
		Path originalDirectory = Paths.get(localName);
		Path newDirectory = Paths.get(targetName);
		try {
//			Files.copy(originalDirectory, newDirectory);
			operateDir(false, originalDirectory, newDirectory);
		} catch (Exception e) {
			throw new RuntimeException("复制文件夹失败", e);
		}
	}

	/**
	 * 复制/移动文件夹
	 * 
	 * @param move    操作标记，为true时移动文件夹,否则为复制
	 * @param source  要复制/移动的源文件夹
	 * @param target  源文件夹要复制/移动到的目标文件夹
	 * @param options 文件复制选项
	 * @throws IOException
	 * @see Files#move(Path, Path, CopyOption...)
	 * @see Files#copy(Path, Path, CopyOption...)
	 * @see Files#walkFileTree(Path, java.nio.file.FileVisitor)
	 */
	public static void operateDir(boolean move, Path source, Path target, CopyOption... options) throws IOException {
		if (null == source || !Files.isDirectory(source))
			throw new IllegalArgumentException("source必须是文件夹");
//		Path dest = target.resolve(source.getFileName());
		// 如果相同则返回
		if (Files.exists(target) && Files.isSameFile(source, target))
			return;
		// 目标文件夹不能是源文件夹的子文件夹
		if (isSub(source, target))
			throw new IllegalArgumentException("dest 不能是source的子文件夹");
		boolean clear = true;
		for (CopyOption option : options)
			if (StandardCopyOption.REPLACE_EXISTING == option) {
				clear = false;
				break;
			}
		// 如果指定了REPLACE_EXISTING选项则不清除目标文件夹
		if (clear) {
			deleteIfExists(target);
		}
		Files.walkFileTree(source, new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				// 在目标文件夹中创建dir对应的子文件夹
				Path subDir = null;
				if (dir.compareTo(source) == 0) {
					subDir = target;
				} else {
					subDir = target.resolve(dir.subpath(source.getNameCount(), dir.getNameCount()));
				}
//				
//				Path subDir = 0 == dir.compareTo(source) ? dest
//						: dest.resolve(dir.subpath(source.getNameCount(), dir.getNameCount()));
				Files.createDirectories(subDir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (move)
					Files.move(file, target.resolve(file.subpath(source.getNameCount(), file.getNameCount())), options);
				else
					Files.copy(file, target.resolve(file.subpath(source.getNameCount(), file.getNameCount())), options);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				// 移动操作时删除源文件夹
				if (move)
					deleteIfExists(dir);
				return super.postVisitDirectory(dir, exc);
			}
		});
	}

	/**
	 * 强制删除文件/文件夹(含不为空的文件夹)<br>
	 * 
	 * @param dir
	 * @throws IOException
	 * @see Files#deleteIfExists(Path)
	 * @see Files#walkFileTree(Path, java.nio.file.FileVisitor)
	 */
	public static boolean deleteIfExists(Path dir) throws IOException {
		try {
			Files.deleteIfExists(dir);
		} catch (DirectoryNotEmptyException e) {
			Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return super.postVisitDirectory(dir, exc);
				}
			});
		}
		return !Files.exists(dir);
	}

	/**
	 * 判断sub是否与parent相等或在其之下<br>
	 * parent必须存在，且必须是directory,否则抛出{@link IllegalArgumentException}
	 * 
	 * @param parent
	 * @param sub
	 * @return
	 * @throws IOException
	 */
	public static boolean sameOrSub(Path parent, Path sub) throws IOException {
		if (null == parent)
			throw new NullPointerException("parent is null");
		if (!Files.exists(parent) || !Files.isDirectory(parent))
			throw new IllegalArgumentException(String.format("the parent not exist or not directory %s", parent));
		while (null != sub) {
			if (Files.exists(sub) && Files.isSameFile(parent, sub))
				return true;
			sub = sub.getParent();
		}
		return false;
	}

	/**
	 * 判断sub是否在parent之下的文件或子文件夹<br>
	 * parent必须存在，且必须是directory,否则抛出{@link IllegalArgumentException}
	 * 
	 * @param parent
	 * @param sub
	 * @return
	 * @throws IOException
	 * @see {@link #sameOrSub(Path, Path)}
	 */
	public static boolean isSub(Path parent, Path sub) throws IOException {
		return (null == sub) ? false : sameOrSub(parent, sub.getParent());
	}

	/**
	 * 创建一个空文件，如果存在同名文件则先删除再创建<br>
	 * 注意：如果lines参数有内容，则创建时先创建tmp文件，然后再改名，以避免某些情况下因文件未写完被提前读取的情况
	 * 
	 * @param file
	 * @param lines 创建后准备写入的文本行，如果没有则不写入
	 */
	public static void createFileWithLines(File file, String... lines) {
		boolean de;
		try {
			de = deleteIfExists(file.toPath());
			if (!de) {
				throw new RuntimeException("存在同名文件，删除失败：" + file.getAbsolutePath());
			}
		} catch (IOException e2) {
			throw new RuntimeException("存在同名文件，删除失败：" + file.getAbsolutePath(), e2);
		}
		Path p = null;
		boolean useTmp = false;
		File tmpFile;
		int count = 0;
		if (lines != null && lines.length > 0) {
			useTmp = true;
			count = lines.length;
			String tmpname = file.getAbsolutePath() + ".tmp";
			tmpFile = new File(tmpname);
			p = tmpFile.toPath();
		} else {
			tmpFile = file;
			p = tmpFile.toPath();
		}

		try {
			boolean del = deleteIfExists(p);
			if (!del) {
				throw new RuntimeException("删除同名文件失败：" + tmpFile.getAbsolutePath());
			}
			boolean re = tmpFile.createNewFile();
			if (!re) {
				throw new RuntimeException("创建文件失败，createNewFile=false：" + tmpFile.getAbsolutePath());
			}
			FileOutputStream fos = null;
			OutputStreamWriter os = null;
			PrintWriter pw = null;
			try {
				fos = new FileOutputStream(tmpFile);
				os = new OutputStreamWriter(fos, "UTF-8");
				pw = new PrintWriter(os);
				int index = 1;
				for (String str : lines) {
					// 行与行之间的分隔符 相当于“\n”
					if (index < count) {
						pw.write((str + System.getProperty("line.separator")).toCharArray());
					} else {
						pw.write(str.toCharArray());
					}
					index++;
					pw.flush();
				}

			} catch (IOException e1) {
				throw e1;
			} finally {
				if (pw != null) {
					pw.close();
					pw = null;
				}
				if (os != null) {
					os.close();
					os = null;
				}
				if (fos != null) {
					fos.close();
					fos = null;
				}
			}
			if (useTmp) {
				Files.move(tmpFile.toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
//				re=tmpFile.renameTo(file);
			}
		} catch (Exception e) {
			throw new RuntimeException("创建文件失败:" + tmpFile.getAbsolutePath(), e);
		}

	}

	/**
	 * 创建一个空文件，如果存在同名文件则先删除再创建<br>
	 * 注意：如果line参数有内容，则创建时先创建tmp文件，然后再改名，以避免某些情况下因文件未写完被提前读取的情况
	 * 
	 * @param file
	 * @param line 创建后准备写入的文本行，如果没有则不写入
	 */
	public static void createFileWithLine(File file, String line) {
		Path p = null;
		boolean useTmp = false;
		File tmpFile;
		if (line != null && line.length() > 0) {
			useTmp = true;
			String tmpname = file.getAbsolutePath() + ".tmp";
			tmpFile = new File(tmpname);
			p = tmpFile.toPath();
		} else {
			tmpFile = file;
			p = tmpFile.toPath();
		}

		try {
			boolean del = deleteIfExists(p);
			if (!del) {
				throw new RuntimeException("删除同名文件失败：" + tmpFile.getAbsolutePath());
			}
			boolean re = tmpFile.createNewFile();
			if (!re) {
				throw new RuntimeException("创建文件失败，createNewFile=false：" + tmpFile.getAbsolutePath());
			}
			FileOutputStream fos = null;
			OutputStreamWriter os = null;
			PrintWriter pw = null;
			try {
				fos = new FileOutputStream(tmpFile);
				os = new OutputStreamWriter(fos, "UTF-8");// "UTF-8");
				pw = new PrintWriter(os);
				pw.write(line.toCharArray());
				pw.flush();
			} catch (IOException e1) {
				throw e1;
			} finally {
				if (pw != null) {
					pw.close();
					pw = null;
				}
				if (os != null) {
					os.close();
					os = null;
				}
				if (fos != null) {
					fos.close();
					fos = null;
				}
			}
			if (useTmp) {
				Files.move(tmpFile.toPath(), file.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			throw new RuntimeException("创建文件失败:" + tmpFile.getAbsolutePath(), e);
		}

	}

	/**
	 * 向一个文件中追加写入内容，注意如果写入的内容有多行，则会自动追加写入换行符System.getProperty("line.separator"）
	 * @param charsetName {@link java.nio.charset.Charset}
	 * @param resultRecord 待追加写入的文件
	 * @param lines 待写入的内容
	 */
	public static void appendWriteFile( String charsetName ,File resultRecord,String... lines) {
		int count = 0;
		if (lines != null && lines.length > 0) {
			count = lines.length;
		}

		try {
			FileOutputStream fos = null;
			OutputStreamWriter os = null;
			PrintWriter pw = null;
			try {
				fos = new FileOutputStream(resultRecord, true);
				os = new OutputStreamWriter(fos, charsetName);
				pw = new PrintWriter(os);
				int index = 1;
				for (String str : lines) {
					// 行与行之间的分隔符 相当于“\n”
					if (index < count) {
						pw.write((str + System.getProperty("line.separator")).toCharArray());
					} else {
						pw.write(str.toCharArray());
					}
					index++;
					pw.flush();
				}

			} catch (IOException e) {
				throw e;
			} finally {
				if (pw != null) {
					pw.close();
					pw = null;
				}
				if (os != null) {
					os.close();
					os = null;
				}
				if (fos != null) {
					fos.close();
					fos = null;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("写文件失败:" + resultRecord.getAbsolutePath(), e);
		}

	}
	public static void appendWriteFileUTF8(File resultRecord, String... lines) {
		appendWriteFile( "UTF-8",resultRecord,  lines) ;
	}
	/**
	 * 从给定的文本文件中读取信息，并按行存入List
	 * @param file 待读取的文件
	 * @param charsetName  {@link java.nio.charset.Charset}
	 * @return 文件内容字符串列表
	 * @throws Exception 当文件读取过程中发生异常则抛出
	 */
	public static List<String> readFile(File file,String charsetName ) throws Exception {
		List<String >result=new ArrayList<String>();
		if (file.exists() == false) {
			return result;
		}
		// 分析文件
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),charsetName));
			String temp = reader.readLine();
			while (temp != null) {
				result.add(temp);
				temp = reader.readLine();
			}
			return result;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws Exception {
		createFileWithLine(new File("D:\\2.txt"), "白日依山尽");
		createFileWithLines(new File("D:\\1.txt"), "白日依山尽", "黄河入海流", "Rest in reason,Move in passion", "19850311");
		appendWriteFileUTF8(new File("D:\\1.txt"), "\r\n追加追加");
		appendWriteFileUTF8(new File("D:\\1.txt"), "追加追加2");
		appendWriteFileUTF8(new File("D:\\1.txt"), "追加追加3", "4", "5");
		
		List<String>list=readFile(new File("D:\\1.txt"),"UTF-8");
		System.out.println(list);
//		System.out.println(getMainFileName("D:\\abc.\\1.png"));
//		System.out.println(getMainFileName(new File("D:\\3")));
	}

}
