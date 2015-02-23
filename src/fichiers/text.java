package fichiers;

import java.io.*;

public class text
{

    private String nom;
    private String etat;
    public static final String F = "F";
    public static final String IN = "IN";
    public static final String OUT = "OUT";
    public static final char TAB = 9;
    private int errCode;
    public static final int CLOSEERR = 10;
    public static final int FERR = 20;
    public static final int INERR = 23;
    public static final int RESETERR = 21;
    public static final int READERR = 22;
    public static final int EOFERR = 51;
    public static final int EOLNERR = 52;
    public static final int NUMERR = 70;
    public static final int REWRITEERR = 110;
    public static final int WRITEERR = 111;
    public static final int OUTERR = 112;
    private BufferedReader input;
    private BufferedWriter output;
    private boolean eof;
    private char buf[];
    private int i;

    public text(String s)
    {
        errCode = 0;
        nom = s;
        etat = "F";
    }

    public void close()
    {
        errCode = 0;
        if(etat == "IN")
        {
            try
            {
                input.close();
                etat = "F";
            }
            catch(Exception _ex)
            {
                errCode = 10;
            }
        }
        if(etat == "OUT")
        {
            try
            {
                output.close();
                etat = "F";
            }
            catch(Exception _ex)
            {
                errCode = 10;
            }
        }
        if(etat == "F")
        {
            errCode = 20;
        }
    }

    public boolean eof()
    {
        errCode = 0;
        if(etat == "IN")
        {
            return eof;
        } else
        {
            errCode = 23;
            return true;
        }
    }

    public boolean eoln()
    {
        errCode = 0;
        if(etat == "IN")
        {
            if(!eof)
            {
                return i == buf.length;
            } else
            {
                errCode = 51;
                return true;
            }
        } else
        {
            errCode = 23;
            return true;
        }
    }

    public char first_char()
    {
        errCode = 0;
        if(etat == "IN")
        {
            if(!eof)
            {
                if(i != buf.length)
                {
                    return buf[i];
                } else
                {
                    return ' ';
                }
            } else
            {
                return ' ';
            }
        } else
        {
            errCode = 23;
            return ' ';
        }
    }

    public int ioError()
    {
        return errCode;
    }

    public String readString()
    {
        errCode = 0;
        if(etat == "IN")
        {
            if(!eof)
            {
                char ac[] = new char[buf.length - i];
                int j = 0;
                for(; i != buf.length; i++)
                {
                    ac[j] = buf[i];
                    j++;
                }

                return String.valueOf(ac);
            } else
            {
                return "";
            }
        } else
        {
            errCode = 23;
            return "";
        }
    }

    public String readStringUntilSep()
    {
        String s = "";
        boolean flag = false;
        char c = read_char();
        if((c == '\t') | (c == ' '))
        {
            flag = true;
        }
        while(!flag) 
        {
            s = s + c;
            if(eof() || eoln())
            {
                flag = true;
            } else
            {
                c = read_char();
                if((c == '\t') | (c == ' '))
                {
                    flag = true;
                }
            }
        }
        return s;
    }

    public String readStringUntilTab()
    {
        String s = "";
        boolean flag = false;
        char c = read_char();
        if(c == '\t')
        {
            flag = true;
        }
        while(!flag) 
        {
            s = s + c;
            if(eof() || eoln())
            {
                flag = true;
            } else
            {
                c = read_char();
                if(c == '\t')
                {
                    flag = true;
                }
            }
        }
        return s;
    }

    public byte read_byte()
    {
        return (byte)(int)read_long();
    }

    public char read_char()
    {
        errCode = 0;
        if(etat == "IN")
        {
            if(!eof)
            {
                if(i != buf.length)
                {
                    return buf[i++];
                } else
                {
                    errCode = 52;
                    return ' ';
                }
            } else
            {
                errCode = 51;
                return ' ';
            }
        } else
        {
            errCode = 23;
            return ' ';
        }
    }

    public double read_double()
    {
        errCode = 0;
        if(etat == "IN")
        {
            skipSeparators();
            if(!eof)
            {
                int j;
                for(j = i; j != buf.length && buf[j] != ' '; j++) { }
                char ac[] = new char[j - i];
                int k = i;
                int l = 0;
                for(; i != j; i++)
                {
                    ac[l] = buf[i];
                    l++;
                }

                try
                {
                    return Double.valueOf(String.valueOf(ac)).doubleValue();
                }
                catch(Exception _ex)
                {
                    errCode = 70;
                }
                i = k;
                return -5D;
            } else
            {
                errCode = 51;
                return 237D;
            }
        } else
        {
            errCode = 23;
            return 567D;
        }
    }

    public float read_float()
    {
        return (float)read_double();
    }

    public int read_int()
    {
        return (int)read_long();
    }

    public long read_long()
    {
        errCode = 0;
        if(etat == "IN")
        {
            skipSeparators();
            if(!eof)
            {
                int j = i;
                if(buf[j] == '-')
                {
                    j++;
                }
                for(; j != buf.length && (buf[j] >= '0') & (buf[j] <= '9'); j++) { }
                char ac[] = new char[j - i];
                int k = i;
                int l = 0;
                for(; i != j; i++)
                {
                    ac[l] = buf[i];
                    l++;
                }

                try
                {
                    return Long.parseLong(String.valueOf(ac));
                }
                catch(Exception _ex)
                {
                    errCode = 70;
                }
                i = k;
                return -5L;
            } else
            {
                errCode = 51;
                return 237L;
            }
        } else
        {
            errCode = 23;
            return 567L;
        }
    }

    public short read_short()
    {
        return (short)(int)read_long();
    }

    public void readln()
    {
        errCode = 0;
        if(etat == "IN")
        {
            if(!eof)
            {
                try
                {
                    String s = input.readLine();
                    if(s == null)
                    {
                        eof = true;
                    } else
                    {
                        eof = false;
                        buf = s.toCharArray();
                        i = 0;
                    }
                }
                catch(Exception _ex)
                {
                    eof = true;
                    errCode = 22;
                }
            } else
            {
                errCode = 51;
            }
        } else
        {
            errCode = 23;
        }
    }

    public String readlnString()
    {
        String s = readString();
        readln();
        return s;
    }

    public byte readln_byte()
    {
        byte byte0 = read_byte();
        readln();
        return byte0;
    }

    public char readln_char()
    {
        char c = read_char();
        readln();
        return c;
    }

    public double readln_double()
    {
        double d = read_double();
        readln();
        return d;
    }

    public float readln_float()
    {
        float f = read_float();
        readln();
        return f;
    }

    public int readln_int()
    {
        int j = read_int();
        readln();
        return j;
    }

    public long readln_long()
    {
        long l = read_long();
        readln();
        return l;
    }

    public short readln_short()
    {
        short word0 = read_short();
        readln();
        return word0;
    }

    public void reset()
    {
        errCode = 0;
        if(etat == "F")
        {
            try
            {
                input = new BufferedReader(new FileReader(nom));
                etat = "IN";
                try
                {
                    String s = input.readLine();
                    if(s == null)
                    {
                        eof = true;
                    } else
                    {
                        eof = false;
                        buf = s.toCharArray();
                        i = 0;
                    }
                }
                catch(Exception _ex)
                {
                    eof = true;
                    errCode = 22;
                }
            }
            catch(Exception _ex)
            {
                errCode = 21;
            }
        } else
        {
            errCode = 20;
        }
    }

    public void rewrite()
    {
        errCode = 0;
        if(etat == "F")
        {
            try
            {
                output = new BufferedWriter(new FileWriter(nom));
                etat = "OUT";
            }
            catch(Exception _ex)
            {
                errCode = 110;
            }
        } else
        {
            errCode = 20;
        }
    }

    public void skipSeparators()
    {
        if(etat == "IN")
        {
            while(!eof && (i == buf.length || buf[i] == ' ' || buf[i] == '\t')) 
            {
                if(i == buf.length)
                {
                    readln();
                } else
                {
                    i++;
                }
            }
        } else
        {
            errCode = 23;
        }
    }

    public boolean tab()
    {
        errCode = 0;
        if(etat == "IN")
        {
            if(!eof)
            {
                if(i != buf.length)
                {
                    return buf[i] == '\t';
                } else
                {
                    return false;
                }
            } else
            {
                errCode = 51;
                return true;
            }
        } else
        {
            errCode = 23;
            return true;
        }
    }

    public void write(byte byte0)
    {
        write(String.valueOf(byte0));
    }

    public void write(char c)
    {
        errCode = 0;
        if(etat == "OUT")
        {
            try
            {
                output.write(c);
            }
            catch(Exception _ex)
            {
                errCode = 111;
            }
        } else
        {
            errCode = 112;
        }
    }

    public void write(double d)
    {
        write(String.valueOf(d));
    }

    public void write(float f)
    {
        write(String.valueOf(f));
    }

    public void write(int j)
    {
        write(String.valueOf(j));
    }

    public void write(long l)
    {
        write(String.valueOf(l));
    }

    public void write(String s)
    {
        errCode = 0;
        if(etat == "OUT")
        {
            try
            {
                output.write(s, 0, s.length());
            }
            catch(Exception _ex)
            {
                errCode = 111;
            }
        } else
        {
            errCode = 112;
        }
    }

    public void write(short word0)
    {
        write(String.valueOf(word0));
    }

    public void write(boolean flag)
    {
        write(String.valueOf(flag));
    }

    public void writeTab()
    {
        write('\t');
    }

    public void writeln()
    {
        errCode = 0;
        if(etat == "OUT")
        {
            try
            {
                output.newLine();
            }
            catch(Exception _ex)
            {
                errCode = 111;
            }
        } else
        {
            errCode = 112;
        }
    }

    public void writeln(byte byte0)
    {
        write(byte0);
        writeln();
    }

    public void writeln(char c)
    {
        write(c);
        writeln();
    }

    public void writeln(double d)
    {
        write(d);
        writeln();
    }

    public void writeln(float f)
    {
        write(f);
        writeln();
    }

    public void writeln(int j)
    {
        write(j);
        writeln();
    }

    public void writeln(long l)
    {
        write(l);
        writeln();
    }

    public void writeln(String s)
    {
        write(s);
        writeln();
    }

    public void writeln(short word0)
    {
        write(word0);
        writeln();
    }

    public void writeln(boolean flag)
    {
        write(flag);
        writeln();
    }
}
